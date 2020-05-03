package com.faystmax.tradingbot.service.exchange;

import com.faystmax.exchangerates.api.client.domain.RateBase;

import java.math.BigDecimal;

public interface ExchangeService {
    /**
     * @param quantity amount that you that to convert
     * @return converted quantity from Usd to Rub
     */
    BigDecimal convertUsdToRub(BigDecimal quantity);

    /**
     * @param form     source currency
     * @param to       destination currency
     * @param quantity amount that you that to convert
     * @return converted quantity
     */
    BigDecimal convert(RateBase form, RateBase to, BigDecimal quantity);
}
