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
public class BuyMarketCommand implements Command {
    public static final String BUY_MARKET_CODE = "BuyMarket";
    private static final String BUY_MARKET_DESCRIPTION = "buyMarket.description";

    private final MessageSource messageSource;
    private final TradeService tradeService;

    @Override
    public String getCode() {
        return BUY_MARKET_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(BUY_MARKET_DESCRIPTION);
    }

    @Override
    public String execute(User user, Collection<String> args) {
        Order order = tradeService.marketBuyAll(user);
        return CommandHelper.getOrderCompletedMsg(order);
    }
}
