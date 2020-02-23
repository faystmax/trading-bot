/*
 * Copyright (c)  2020, Amosov Maxim, faystmax@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.faystmax.tradingbot.service.binance.impl;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.general.SymbolInfo;
import com.faystmax.tradingbot.config.BinanceConfig;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.binance.Balance;
import com.faystmax.tradingbot.service.binance.BinanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class BinanceServiceImpl implements BinanceService {
    private final BinanceConfig config;
    private final BinanceApiRestClient client;

    @Autowired
    public BinanceServiceImpl(OrderRepo orderRepo, BinanceConfig config) {
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
    public Pair<Balance, Balance> getCurrentBalance() {
        Account account = client.getAccount();
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        AssetBalance baseBalance = account.getAssetBalance(symbolInfo.getBaseAsset());
        AssetBalance quoteBalance = account.getAssetBalance(symbolInfo.getQuoteAsset());
        return Pair.of(Balance.valueOf(baseBalance), Balance.valueOf(quoteBalance));
    }

    @Override
    public NewOrderResponse marketBuy(BigDecimal quantity) {
        NewOrder newOrder = NewOrder.marketBuy(config.getSymbol(), quantity.toPlainString());
        log.info("Creating order = {}", newOrder);
        NewOrderResponse newOrderResponse = client.newOrder(newOrder);
        log.info("Order created = {}", newOrderResponse);
        return client.newOrder(newOrder);
    }
}
