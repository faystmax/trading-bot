package com.faystmax.tradingbot.service.exchange.impl;

import com.faystmax.exchangerates.api.client.ExRatesApiClient;
import com.faystmax.exchangerates.api.client.domain.Currency;
import com.faystmax.exchangerates.api.client.domain.ExchangeRates;
import com.faystmax.tradingbot.service.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final ExRatesApiClient apiClient;

    @Override
    public BigDecimal convertUsdToRub(final BigDecimal quantity) {
        return convert(Currency.USD, Currency.RUB, quantity);
    }

    @Override
    public BigDecimal convert(final Currency form, final Currency to, final BigDecimal quantity) {
        ExchangeRates latestRates = apiClient.getLatestRates(form);
        BigDecimal rate = latestRates.getRates().get(to);
        return rate.multiply(quantity);
    }
}
