package com.faystmax.tradingbot.service.binance;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.impl.BinanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BinanceServiceFactory {
    public BinanceService createBinanceService(User user) {
        return new BinanceServiceImpl(user);
    }
}
