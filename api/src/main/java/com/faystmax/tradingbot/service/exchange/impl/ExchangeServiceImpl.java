package com.faystmax.tradingbot.service.exchange.impl;

import com.faystmax.exchangerates.api.client.ExRatesApiClient;
import com.faystmax.exchangerates.api.client.domain.ExRatesInfo;
import com.faystmax.exchangerates.api.client.domain.RateBase;
import com.faystmax.tradingbot.service.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final ExRatesApiClient apiClient;

    @Override
    public BigDecimal convert(final RateBase formRate, final RateBase toRate, final BigDecimal quantity) {
        ExRatesInfo latestRates = apiClient.getLatestRates(formRate);
        BigDecimal price = latestRates.getRates().get(toRate);
        return price.multiply(quantity);
    }
}
