package com.faystmax.tradingbot.service.binance.impl;

import com.faystmax.binance.api.client.BinanceApiClient;
import com.faystmax.binance.api.client.BinanceApiClientFactory;
import com.faystmax.binance.api.client.domain.SymbolFilter;
import com.faystmax.binance.api.client.domain.SymbolInfo;
import com.faystmax.binance.api.client.domain.account.Account;
import com.faystmax.binance.api.client.domain.account.AssetBalance;
import com.faystmax.binance.api.client.domain.enums.FilterType;
import com.faystmax.binance.api.client.domain.request.AllOrdersRequest;
import com.faystmax.binance.api.client.domain.trade.NewOrder;
import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.binance.api.client.domain.trade.NewOrderResponseType;
import com.faystmax.binance.api.client.domain.trade.Order;
import com.faystmax.binance.api.client.domain.trade.Trade;
import com.faystmax.tradingbot.config.BinanceConfig;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.model.Balance;
import com.faystmax.tradingbot.service.binance.model.FullBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class BinanceServiceImpl implements BinanceService {
    private final BinanceConfig config;
    private final BinanceApiClient client;

    @Autowired
    public BinanceServiceImpl(BinanceConfig config) {
        this.client = BinanceApiClientFactory.create(config.getApiKey(), config.getSecretKey());
        this.config = config;
    }

    @Override
    public String getTradingSymbol() {
        return config.getSymbol();
    }

    @Override
    public BigDecimal getLastPrice() {
        return client.getLatestPrice(config.getSymbol()).getPrice();
    }

    @Override
    public List<Trade> getMyTrades(Integer limit) {
        return client.getMyTrades(config.getSymbol(), limit);
    }

    @Override
    public List<Order> getAllMyOrders() {
        var request = new AllOrdersRequest(config.getSymbol());
        return client.getAllOrders(request);
    }

    @Override
    public FullBalance getCurrentBalance() {
        Account account = client.getAccount();
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        AssetBalance baseBalance = account.getAssetBalance(symbolInfo.getBaseAsset());
        AssetBalance quoteBalance = account.getAssetBalance(symbolInfo.getQuoteAsset());
        return new FullBalance(Balance.valueOf(baseBalance), Balance.valueOf(quoteBalance));
    }

    @Override
    public NewOrderResponse marketBuyAll() {
        FullBalance balance = getCurrentBalance();
        BigDecimal quoteFreeQuantity = balance.getQuote().getFree();
        return marketBuyQuoteQty(quoteFreeQuantity);
    }

    @Override
    public NewOrderResponse marketBuy(final BigDecimal quantity) {
        BigDecimal correctQuantity = cutQuantity(quantity);
        NewOrder newOrder = NewOrder.marketBuy(config.getSymbol(), correctQuantity);
        newOrder.setNewOrderRespType(NewOrderResponseType.FULL);
        return createOrder(newOrder);
    }

    @Override
    public NewOrderResponse marketBuyQuoteQty(BigDecimal quoteQuantity) {
        NewOrder newOrder = NewOrder.marketBuyQuoteQty(config.getSymbol(), quoteQuantity);
        newOrder.setNewOrderRespType(NewOrderResponseType.FULL);
        return createOrder(newOrder);
    }

    @Override
    public NewOrderResponse marketSellAll() {
        FullBalance balance = getCurrentBalance();
        BigDecimal baseFreeQuantity = balance.getBase().getFree();
        return marketSell(baseFreeQuantity);
    }

    @Override
    public NewOrderResponse marketSell(final BigDecimal quantity) {
        BigDecimal correctQuantity = cutQuantity(quantity);
        NewOrder newOrder = NewOrder.marketSell(config.getSymbol(), correctQuantity);
        newOrder.setNewOrderRespType(NewOrderResponseType.FULL);
        return createOrder(newOrder);
    }

    /**
     * Cut quantity with stepSize
     *
     * @param quantity value to cut
     * @return return cutted quantity
     */
    private BigDecimal cutQuantity(final BigDecimal quantity) {
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        SymbolFilter symbolFilter = symbolInfo.getSymbolFilter(FilterType.LOT_SIZE);
        BigDecimal stepSize = symbolFilter.getStepSize();
        return quantity.subtract(quantity.remainder(stepSize.multiply(BigDecimal.valueOf(2))));
    }

    /**
     * Create new order in Binance
     *
     * @param newOrder order to create
     * @return order response
     */
    private NewOrderResponse createOrder(final NewOrder newOrder) {
        log.info("Creating order = {}", newOrder);
        NewOrderResponse newOrderResponse = client.newOrder(newOrder);
        log.info("Order created = {}", newOrderResponse);
        return newOrderResponse;
    }
}
