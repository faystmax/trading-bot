package com.faystmax.tradingbot.service.binance.impl;

import com.faystmax.binance.api.client.BinanceApiClient;
import com.faystmax.binance.api.client.BinanceApiClientFactory;
import com.faystmax.binance.api.client.domain.ExchangeInfo;
import com.faystmax.binance.api.client.domain.SymbolFilter;
import com.faystmax.binance.api.client.domain.SymbolInfo;
import com.faystmax.binance.api.client.domain.TickerPrice;
import com.faystmax.binance.api.client.domain.account.Account;
import com.faystmax.binance.api.client.domain.account.AssetBalance;
import com.faystmax.binance.api.client.domain.enums.FilterType;
import com.faystmax.binance.api.client.domain.request.AllOrdersRequest;
import com.faystmax.binance.api.client.domain.trade.NewOrder;
import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.binance.api.client.domain.trade.NewOrderResponseType;
import com.faystmax.binance.api.client.domain.trade.Order;
import com.faystmax.binance.api.client.domain.trade.Trade;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.model.Balance;
import com.faystmax.tradingbot.service.binance.model.Commission;
import com.faystmax.tradingbot.service.binance.model.FullBalance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@EnableCaching
@RequiredArgsConstructor
public class BinanceServiceImpl implements BinanceService {
    @Value("${binance.base-commission}")
    private final BigDecimal baseCommission;

    @Override
    public ExchangeInfo getExchangeInfo(final User user) {
        return createClient(user).getExchangeInfo();
    }

    @Override
    public BigDecimal getTotalUsdtAmount(final User user) {
        final BinanceApiClient client = createClient(user);
        final List<TickerPrice> latestPrices = client.getLatestPrice();
        final List<AssetBalance> balances = client.getAccount().getBalances();
        final Map<String, BigDecimal> pricesBySymbol = latestPrices.stream().collect(toMap(TickerPrice::getSymbol, TickerPrice::getPrice));
        return balances.stream()
            .filter(balance -> ZERO.compareTo(balance.getFree()) != 0 || ZERO.compareTo(balance.getLocked()) != 0)
            .map(balance -> {
                if (balance.getAsset().equals("USDT")) {
                    return balance.getFree().add(balance.getLocked());
                }
                final BigDecimal balanceInUSDT = pricesBySymbol.getOrDefault(balance.getAsset() + "USDT", ZERO);
                return balanceInUSDT.multiply(balance.getFree().add(balance.getLocked()));
            }).reduce(ZERO, BigDecimal::add);
    }

    @Override
    public List<String> getActiveSymbols(final User user) {
        final BinanceApiClient client = createClient(user);
        final List<AssetBalance> balances = client.getAccount().getBalances();
        return balances.stream()
            .filter(balance -> ZERO.compareTo(balance.getFree()) != 0 || ZERO.compareTo(balance.getLocked()) != 0)
            .filter(balance -> !balance.getAsset().equals("USDT"))
            .map(balance -> balance.getAsset() + "USDT")
            .collect(Collectors.toList());
    }

    @Override
    public List<TickerPrice> getAllLastPrices(final User user) {
        return createClient(user).getLatestPrice();
    }

    @Override
    public BigDecimal getLastPrice(final User user, final String symbol) {
        return createClient(user).getLatestPrice(symbol).getPrice();
    }

    @Override
    public List<Trade> getMyTrades(final User user, final Integer limit) {
        return createClient(user).getMyTrades(user.getTradingSymbol(), limit);
    }

    @Override
    public List<Order> getAllOrdersForSymbol(final User user, final String symbol) {
        final var request = new AllOrdersRequest(symbol);
        return createClient(user).getAllOrders(request);
    }

    @Override
    public FullBalance getCurrentBalance(final User user) {
        final Account account = createClient(user).getAccount();
        final SymbolInfo symbolInfo = createClient(user).getExchangeInfo().getSymbolInfo(user.getTradingSymbol());
        final AssetBalance baseBalance = account.getAssetBalance(symbolInfo.getBaseAsset());
        final AssetBalance quoteBalance = account.getAssetBalance(symbolInfo.getQuoteAsset());
        return new FullBalance(Balance.valueOf(baseBalance), Balance.valueOf(quoteBalance));
    }

    @Override
    public Commission getCommission(final User user) {
        final Account account = createClient(user).getAccount();
        return Commission.of(account.getMakerCommission(), account.getTakerCommission(), baseCommission);
    }

    @Override
    public NewOrderResponse marketBuyAll(final User user) {
        final FullBalance balance = getCurrentBalance(user);
        final BigDecimal quoteFreeQuantity = balance.getQuote().getFree();
        return marketBuyQuoteQty(user, quoteFreeQuantity);
    }

    @Override
    public NewOrderResponse marketBuy(final User user, final BigDecimal quantity) {
        final BigDecimal correctQuantity = cutQuantity(user, quantity);
        final NewOrder newOrder = NewOrder.marketBuy(user.getTradingSymbol(), correctQuantity);
        newOrder.setNewOrderRespType(NewOrderResponseType.FULL);
        return createOrder(user, newOrder);
    }

    @Override
    public NewOrderResponse marketBuyQuoteQty(final User user, final BigDecimal quoteQuantity) {
        final NewOrder newOrder = NewOrder.marketBuyQuoteQty(user.getTradingSymbol(), quoteQuantity);
        newOrder.setNewOrderRespType(NewOrderResponseType.FULL);
        return createOrder(user, newOrder);
    }

    @Override
    public NewOrderResponse marketSellAll(final User user) {
        final FullBalance balance = getCurrentBalance(user);
        final BigDecimal baseFreeQuantity = balance.getBase().getFree();
        return marketSell(user, baseFreeQuantity);
    }

    @Override
    public NewOrderResponse marketSell(final User user, final BigDecimal quantity) {
        final BigDecimal correctQuantity = cutQuantity(user, quantity);
        final NewOrder newOrder = NewOrder.marketSell(user.getTradingSymbol(), correctQuantity);
        newOrder.setNewOrderRespType(NewOrderResponseType.FULL);
        return createOrder(user, newOrder);
    }

    @Override
    public NewOrderResponse marketSellAll(final User user, final String symbol) {
        final Account account = createClient(user).getAccount();
        final AssetBalance balance = account.getAssetBalance(symbol);
        final BigDecimal baseFreeQuantity = balance.getFree();

        final BigDecimal correctQuantity = cutQuantity(user, symbol, baseFreeQuantity);
        final NewOrder newOrder = NewOrder.marketSell(symbol, correctQuantity);
        return createOrder(user, newOrder);
    }

    @Override
    @Cacheable("latestPrice")
    public List<TickerPrice> getLatestPrice(final User user) {
        return createClient(user).getLatestPrice();
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 30 * 1000)
    @CacheEvict(value = "latestPrice", allEntries = true)
    public void clearCache() {
        log.info("Clear cache latestPrice");
    }

    /**
     * Cut quantity with stepSize
     *
     * @param quantity value to cut
     * @return return cutted quantity
     */
    private BigDecimal cutQuantity(final User user, final BigDecimal quantity) {
        return cutQuantity(user, user.getTradingSymbol(), quantity);
    }

    /**
     * Cut quantity with stepSize
     *
     * @param quantity value to cut
     * @return return cutted quantity
     */
    private BigDecimal cutQuantity(final User user, final String symbol, final BigDecimal quantity) {
        final SymbolInfo symbolInfo = createClient(user).getExchangeInfo().getSymbolInfo(symbol);
        final SymbolFilter symbolFilter = symbolInfo.getSymbolFilter(FilterType.LOT_SIZE);
        final BigDecimal stepSize = symbolFilter.getStepSize();
        return quantity.subtract(quantity.remainder(stepSize.multiply(BigDecimal.valueOf(1))));
    }

    /**
     * Create new order in Binance
     *
     * @param newOrder order to create
     * @return order response
     */
    private NewOrderResponse createOrder(final User user, final NewOrder newOrder) {
        log.info("Creating order = {}", newOrder);
        final NewOrderResponse newOrderResponse = createClient(user).newOrder(newOrder);
        log.info("Order created = {}", newOrderResponse);
        return newOrderResponse;
    }

    private BinanceApiClient createClient(final User user) {
        return BinanceApiClientFactory.create(user.getBinanceApiKey(), user.getBinanceSecretKey());
    }
}
