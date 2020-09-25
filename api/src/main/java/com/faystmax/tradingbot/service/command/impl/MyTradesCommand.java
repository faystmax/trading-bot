package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.binance.api.client.domain.trade.Trade;
import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyTradesCommand implements Command {
    private static final String MY_TRADES_CODE = "MyTrades";
    private static final String MY_TRADES_DESCRIPTION = "myTrades.description";
    private static final String MY_TRADES_EMPTY = "myTrades.empty";

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
        builder.append("My last trades of \"").append(binanceService.getTradingSymbol()).append("\":\n");
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
