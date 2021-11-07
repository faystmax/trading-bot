package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.service.command.CommandHelper;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SellMarketCommand implements Command {
    public static final String SELL_MARKET_CODE = "SellMarket";
    private static final String SELL_MARKET_DESCRIPTION = "sellMarket.description";

    private final MessageSource messageSource;
    private final TradeService tradeService;

    @Override
    public String getCode() {
        return SELL_MARKET_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(SELL_MARKET_DESCRIPTION);
    }

    @Override
    public String execute(final User user, final Collection<String> args) {
        final Order order = tradeService.marketSellAll(user);
        return CommandHelper.getOrderCompletedMsg(order);
    }
}
