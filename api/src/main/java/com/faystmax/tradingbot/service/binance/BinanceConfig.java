package com.faystmax.tradingbot.service.binance;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.impl.BinanceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class BinanceConfig {
    @Bean
    public Function<User, BinanceService> beanFactory() {
        return BinanceServiceImpl::new;
    }
}
