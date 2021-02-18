package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.BinanceServiceFactory;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Displays current price of selected symbol
 *
 * @see User#getTradingSymbol()
 */
@Component
@RequiredArgsConstructor
public class CurrentPriceCommand implements Command {
    private final static String CURRENT_PRICE_CODE = "CurrentPrice";
    private final static String CURRENT_PRICE_ANSWER = "currentPrice.answer";
    private final static String CURRENT_PRICE_DESCRIPTION = "currentPrice.description";

    private final MessageSource messageSource;
    private final BinanceServiceFactory binanceServiceFactory;

    @Override
    public String getCode() {
        return CURRENT_PRICE_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(CURRENT_PRICE_DESCRIPTION);
    }

    @Override
    public String execute(User user, Collection<String> args) {
        BinanceService binanceService = binanceServiceFactory.createBinanceService(user);
        BigDecimal lastPrice = binanceService.getLastPrice();
        return messageSource.getMsg(CURRENT_PRICE_ANSWER, user.getTradingSymbol(), lastPrice.toPlainString());
    }
}
