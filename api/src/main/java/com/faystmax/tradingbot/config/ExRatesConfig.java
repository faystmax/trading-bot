package com.faystmax.tradingbot.config;

import com.faystmax.exchangerates.api.client.ExRatesApiClient;
import com.faystmax.exchangerates.api.client.impl.ExRatesApiClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExRatesConfig {
    @Bean
    public ExRatesApiClient exRatesApiClient() {
        return new ExRatesApiClientImpl(true);
    }
}
