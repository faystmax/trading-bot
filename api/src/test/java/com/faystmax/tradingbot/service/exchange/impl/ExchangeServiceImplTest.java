package com.faystmax.tradingbot.service.exchange.impl;

import com.faystmax.exchangerates.api.client.domain.Currency;
import com.faystmax.exchangerates.api.client.domain.ExchangeRates;
import com.faystmax.exchangerates.api.client.impl.ExRatesApiClientImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTest {
    @Mock
    private ExRatesApiClientImpl apiClient;
    @InjectMocks
    private ExchangeServiceImpl service;

    @Test
    @DisplayName("Check convert from Usd to Rub")
    public void convertFromUsdToRubSuccess() {
        var rates = new ExchangeRates();
        rates.setBase(Currency.USD);
        rates.setDate(new Date());
        rates.setRates(Collections.singletonMap(Currency.RUB, new BigDecimal(70)));
        when(apiClient.getLatestRates(any())).thenReturn(rates);

        var res = service.convert(Currency.USD, Currency.RUB, new BigDecimal(1));
        assertEquals(res, new BigDecimal(70));
    }

    @Test
    @DisplayName("Check convert from Usd to Rub")
    public void convertFromRubToUsdSuccess() {
        var rates = new ExchangeRates();
        rates.setBase(Currency.RUB);
        rates.setDate(new Date());
        rates.setRates(Collections.singletonMap(Currency.USD, new BigDecimal(1)));
        when(apiClient.getLatestRates(any())).thenReturn(rates);

        var res = service.convert(Currency.RUB, Currency.USD, new BigDecimal(70));
        assertEquals(res, new BigDecimal(70));
    }
}