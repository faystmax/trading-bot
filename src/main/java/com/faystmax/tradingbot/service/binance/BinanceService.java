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

package com.faystmax.tradingbot.service.binance;

import com.binance.api.client.domain.account.AssetBalance;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Service for interaction with binance
 */
public interface BinanceService {
    /**
     * Test connection to binance
     */
    void ping();

    /**
     * @return last price of selected symbol
     * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
     */
    String getLastPrice();

    /**
     * Return Base and qoute balance
     * (for symbol = "ETHUSDT" it will be "ETH" and "USDT" balances)
     *
     * @return base and quote balances
     */
    Pair<AssetBalance, AssetBalance> getCurrentBalance();
}
