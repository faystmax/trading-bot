package com.faystmax.tradingbot.service.exchange;

import com.faystmax.exchangerates.api.client.domain.RateBase;

import java.math.BigDecimal;

public interface ExchangeService {
    BigDecimal convertUsdToRub(BigDecimal quantity);

    BigDecimal convert(RateBase formRate, RateBase toRate, BigDecimal quantity);
}
