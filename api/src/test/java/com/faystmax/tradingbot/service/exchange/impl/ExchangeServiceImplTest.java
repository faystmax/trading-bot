package com.faystmax.tradingbot.service.exchange.impl;

import com.faystmax.exchangerates.api.client.domain.ExRatesInfo;
import com.faystmax.exchangerates.api.client.domain.RateBase;
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
    private void convertFromUsdToRubSuccess() {
        var ratesInfo = new ExRatesInfo();
        ratesInfo.setBase(RateBase.USD);
        ratesInfo.setDate(new Date());
        ratesInfo.setRates(Collections.singletonMap(RateBase.RUB, new BigDecimal(70)));
        when(apiClient.getLatestRates(any())).thenReturn(ratesInfo);

        var res = service.convert(RateBase.USD, RateBase.RUB, new BigDecimal(1));
        assertEquals(res, new BigDecimal(70));
    }

    @Test
    @DisplayName("Check convert from Usd to Rub")
    private void convertFromRubToUsdSuccess() {
        var ratesInfo = new ExRatesInfo();
        ratesInfo.setBase(RateBase.RUB);
        ratesInfo.setDate(new Date());
        ratesInfo.setRates(Collections.singletonMap(RateBase.USD, new BigDecimal(1)));
        when(apiClient.getLatestRates(any())).thenReturn(ratesInfo);

        var res = service.convert(RateBase.RUB, RateBase.USD, new BigDecimal(70));
        assertEquals(res, new BigDecimal(70));
    }
}