package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Displays current price of selected symbol
 *
 * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CurrentPriceCommand implements Command {
    private final static String CURRENT_PRICE_CODE = "currentPrice";
    private final static String CURRENT_PRICE_ANSWER = "currentPrice.answer";
    private final static String CURRENT_PRICE_DESCRIPTION = "currentPrice.description";

    private final MessageSource messageSource;
    private final BinanceService binanceService;

    @Override
    public String getCode() {
        return CURRENT_PRICE_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(CURRENT_PRICE_DESCRIPTION, binanceService.getTradingSymbol());
    }

    @Override
    public String execute(Collection<String> args) {
        BigDecimal lastPrice = binanceService.getLastPrice();
        return messageSource.getMsg(CURRENT_PRICE_ANSWER, binanceService.getTradingSymbol(), lastPrice.toPlainString());
    }
}
