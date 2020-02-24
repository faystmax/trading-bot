package com.faystmax.tradingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableScheduling
@SpringBootApplication
public class TradingBotApplication {
    public static void main(String[] args) {
        ApiContextInitializer.init(); // For telegram
        SpringApplication.run(TradingBotApplication.class, args);
    }
}
