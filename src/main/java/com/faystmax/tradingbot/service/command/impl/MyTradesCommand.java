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

import com.binance.api.client.domain.account.Trade;
import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.config.BinanceConfig;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MyTradesCommand implements Command {
    private static final String MY_TRADES_CODE = "myTrades";
    private static final String MY_TRADES_DESCRIPTION = "myTrades.description";
    private static final String MY_TRADES_EMPTY = "myTrades.empty";

    private final BinanceConfig config;
    private final MessageSource messageSource;
    private final BinanceService binanceService;

    @Override
    public String getCode() {
        return MY_TRADES_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(MY_TRADES_DESCRIPTION);
    }

    @Override
    public String execute(Collection<String> args) {
        List<Trade> trades = binanceService.getMyTrades();
        if (trades.isEmpty()) {
            return messageSource.getMsg(MY_TRADES_EMPTY);
        }

        var builder = new StringBuilder();
        builder.append("My last trades of \"").append(config.getSymbol()).append("\":\n");
        trades.forEach(trade -> {
            builder.append("OrderId = <b>").append(trade.getOrderId()).append("</b>\n");
            builder.append("Price = <b>").append(trade.getPrice()).append("</b>\n");
            builder.append("Qty = <b>").append(trade.getQty()).append("</b>\n");
            builder.append("QuoteQty = <b>").append(trade.getQuoteQty()).append("</b>\n");
            builder.append("Time = <b>").append(DateUtils.format(trade.getTime())).append("</b>\n");
            builder.append("--------------------------------------------------\n");
        });
        return builder.toString();
    }
}
