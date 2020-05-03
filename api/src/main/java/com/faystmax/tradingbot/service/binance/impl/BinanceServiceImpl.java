package com.faystmax.tradingbot.service.binance.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.general.FilterType;
import com.binance.api.client.domain.general.SymbolFilter;
import com.binance.api.client.domain.general.SymbolInfo;
import com.faystmax.tradingbot.config.BinanceConfig;
import com.faystmax.tradingbot.service.binance.Balance;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.FullBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
public class BinanceServiceImpl implements BinanceService {
    private final BinanceConfig config;
    private final BinanceApiRestClient client;

    @Autowired
    public BinanceServiceImpl(BinanceConfig config) {
        var factory = BinanceApiClientFactory.newInstance(config.getApiKey(), config.getSecretKey());
        this.client = factory.newRestClient();
        this.config = config;
    }

    @Override
    public String getTradingSymbol() {
        return config.getSymbol();
    }

    @Override
    public BigDecimal getLastPrice() {
        return new BigDecimal(client.get24HrPriceStatistics(config.getSymbol()).getLastPrice());
    }

    @Override
    public List<Trade> getMyTrades() {
        return client.getMyTrades(config.getSymbol());
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
    public Order marketBuyAll() {
        FullBalance balance = getCurrentBalance();
        BigDecimal free = balance.getQuote().getFree();
        BigDecimal lastPrice = getLastPrice();
        BigDecimal availableBuyQuantity = free.divide(lastPrice, 8, RoundingMode.DOWN);

        return marketBuy(availableBuyQuantity);
    }

    @Override
    public Order marketBuy(final BigDecimal quantity) {
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        SymbolFilter symbolFilter = symbolInfo.getSymbolFilter(FilterType.LOT_SIZE);
        BigDecimal stepSize = new BigDecimal(symbolFilter.getStepSize());
        BigDecimal finalQuantity = quantity.subtract(quantity.remainder(stepSize));

        NewOrder newOrder = NewOrder.marketBuy(config.getSymbol(), finalQuantity.toPlainString());
        log.info("Creating order = {}", newOrder);

        NewOrderResponse newOrderResponse = client.newOrder(newOrder);
        log.info("Order created = {}", newOrderResponse);
        var orderStatusRequest = new OrderStatusRequest(config.getSymbol(), newOrderResponse.getOrderId());
        return client.getOrderStatus(orderStatusRequest);
    }

    @Override
    public Order marketSellAll() {
        FullBalance balance = getCurrentBalance();
        BigDecimal free = balance.getBase().getFree();
        return marketSell(free);
    }

    @Override
    public Order marketSell(final BigDecimal quantity) {
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        SymbolFilter symbolFilter = symbolInfo.getSymbolFilter(FilterType.LOT_SIZE);
        BigDecimal stepSize = new BigDecimal(symbolFilter.getStepSize());
        BigDecimal finalQuantity = quantity.subtract(quantity.remainder(stepSize));

        NewOrder newOrder = NewOrder.marketSell(config.getSymbol(), finalQuantity.toPlainString());
        log.info("Creating order = {}", newOrder);

        NewOrderResponse newOrderResponse = client.newOrder(newOrder);
        log.info("Order created = {}", newOrderResponse);
        var orderStatusRequest = new OrderStatusRequest(config.getSymbol(), newOrderResponse.getOrderId());
        return client.getOrderStatus(orderStatusRequest);
    }
}
