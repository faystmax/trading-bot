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
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.general.SymbolInfo;
import com.faystmax.tradingbot.config.BinanceConfig;
import com.faystmax.tradingbot.service.binance.BinanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String getLastPrice() {
        return client.get24HrPriceStatistics(config.getSymbol()).getLastPrice();
    }

    @Override
    public Pair<AssetBalance, AssetBalance> getCurrentBalance() {
        Account account = client.getAccount();
        SymbolInfo symbolInfo = client.getExchangeInfo().getSymbolInfo(config.getSymbol());
        AssetBalance baseBalance = account.getAssetBalance(symbolInfo.getBaseAsset());
        AssetBalance quoteBalance = account.getAssetBalance(symbolInfo.getQuoteAsset());
        return Pair.of(baseBalance, quoteBalance);
    }
}
