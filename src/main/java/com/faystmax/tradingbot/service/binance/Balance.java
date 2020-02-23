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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Wrapper to work with balance as BigDecimal
 */
@Getter
@Setter
@NoArgsConstructor
public class Balance {
    /**
     * Asset symbol.
     */
    private String asset;
    /**
     * Available balance.
     */
    private BigDecimal free;
    /**
     * Locked by open orders.
     */
    private BigDecimal locked;

    public BigDecimal getAll() {
        return free.add(locked);
    }

    public static Balance valueOf(final AssetBalance assetBalance) {
        Balance balance = new Balance();
        balance.setAsset(assetBalance.getAsset());
        balance.setFree(new BigDecimal(assetBalance.getFree()));
        balance.setLocked(new BigDecimal(assetBalance.getLocked()));
        return balance;
    }
}
