package com.faystmax.tradingbot.service.binance.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.general.FilterType;
import com.binance.api.client.domain.general.SymbolFilter;
import com.binance.api.client.domain.general.SymbolInfo;
import com.faystmax.tradingbot.config.BinanceConfig;
import com.faystmax.tradingbot.exception.ServiceException;
import com.faystmax.tradingbot.service.binance.Balance;
import com.faystmax.tradingbot.service.binance.BinanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    public void ping() {
        client.ping();
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
    @Transactional(readOnly = true)
    public List<Trade> getMyTrades() {
        return client.getMyTrades(config.getSymbol());
    }

    @Override
    public List<Order> getAllMyOrders() {
        return client.getAllOrders(new AllOrdersRequest(config.getSymbol()));
    }

    @Override
    public List<Order> getAllMyOrders(Long startTime, Long endTime) {
        var request = new AllOrdersRequest(config.getSymbol()).startTime(startTime).endTime(endTime);
        return client.getAllOrders(request);
    }

    @Override
    public Pair<Balance, Balance> getCurrentBalance() {
        Account account = client.getAccount();
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        AssetBalance baseBalance = account.getAssetBalance(symbolInfo.getBaseAsset());
        AssetBalance quoteBalance = account.getAssetBalance(symbolInfo.getQuoteAsset());
        return Pair.of(Balance.valueOf(baseBalance), Balance.valueOf(quoteBalance));
    }

    // TODO: 19.03.2020 refactor
    @Override
    public NewOrderResponse marketBuy(BigDecimal quantity) {
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        Optional<SymbolFilter> lotSizeFilter = symbolInfo.getFilters().stream().filter(v -> v.getFilterType() == FilterType.LOT_SIZE).findFirst();
        if (lotSizeFilter.isPresent()) {
            SymbolFilter symbolFilter = lotSizeFilter.get();
            final BigDecimal stepSize = new BigDecimal(symbolFilter.getStepSize());

            BigDecimal remainder = quantity.remainder(stepSize);
            BigDecimal finalQuantity = quantity.subtract(remainder);

            NewOrder newOrder = NewOrder.marketBuy(config.getSymbol(), finalQuantity.toPlainString());
            log.info("Creating order = {}", newOrder);
            NewOrderResponse newOrderResponse = client.newOrder(newOrder);
            log.info("Order created = {}", newOrderResponse);
            return client.newOrder(newOrder);
        } else {
            throw new ServiceException("Error");
        }
    }

    @Override
    public SymbolInfo getSymbolInfo() {
        return client.getExchangeInfo().getSymbolInfo(config.getSymbol());
    }
}
