package com.faystmax.tradingbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "binance")
public class BinanceConfig {
    /**
     * Main trading symbol (like 'ETHUSDT')
     */
    @NotBlank
    private String symbol;
    /**
     * Api key from binance.com account
     */
    @NotBlank
    private String apiKey;
    /**
     * Secret key from binance.com account
     */
    @NotBlank
    private String secretKey;
}
