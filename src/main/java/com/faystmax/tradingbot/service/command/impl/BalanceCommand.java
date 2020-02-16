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

package com.faystmax.tradingbot.service.command.impl;

import com.binance.api.client.domain.account.AssetBalance;
import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Displays current balance of selected symbol
 *
 * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BalanceCommand implements Command {
    private final static String BALANCE_CODE = "balance";
    private final static String BALANCE_DESCRIPTION = "balance.description";

    private final MessageSource messageSource;
    private final BinanceService binanceService;

    @Override
    public String getCode() {
        return BALANCE_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(BALANCE_DESCRIPTION);
    }

    @Override
    public String execute(Collection<String> args) {
        Pair<AssetBalance, AssetBalance> balancePair = binanceService.getCurrentBalance();
        var builder = new StringBuilder();
        appendBalance(builder, balancePair.getLeft());
        builder.append("\n");
        appendBalance(builder, balancePair.getRight());
        return builder.toString();
    }

    /**
     * Appends balance data to builder
     *
     * @param builder - builder where data will be appended
     * @param balance - balance data to append
     */
    private void appendBalance(final StringBuilder builder, final AssetBalance balance) {
        BigDecimal free = new BigDecimal(balance.getFree());
        BigDecimal locked = new BigDecimal(balance.getLocked());
        builder.append(balance.getAsset()).append(":").append("\n")
            .append("    Free = <b>").append(free.toPlainString()).append("</b>\n")
            .append("    Locked = <b>").append(locked.toPlainString()).append("</b>\n")
            .append("    All = <b>").append(free.add(locked).toPlainString()).append("</b>\n");
    }
}
