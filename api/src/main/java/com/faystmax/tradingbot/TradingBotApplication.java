package com.faystmax.tradingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TradingBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradingBotApplication.class, args);
    }
}
