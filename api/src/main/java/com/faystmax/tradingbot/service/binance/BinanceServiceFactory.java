package com.faystmax.tradingbot.service.binance;

import com.faystmax.tradingbot.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class BinanceServiceFactory {
    private BiFunction<String, String, BinanceService> beanFactory;

    public BinanceService createBinanceService(User user) {
        return beanFactory.apply(user.getBinanceApiKey(), user.getBinanceSecretKey());
    }
}
