package com.faystmax.tradingbot.service.trade.impl;

import com.binance.api.client.domain.account.Order;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.repo.OrderRepoService;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TradeServiceImpl implements TradeService {
    private final BinanceService binanceService;
    private final OrderRepoService orderRepoService;

    @Transactional
    @Override
    public com.faystmax.tradingbot.db.entity.Order marketBuyAll() {
        Order order = binanceService.marketBuyAll();
        return orderRepoService.createOrder(order);
    }

    @Transactional
    @Override
    public com.faystmax.tradingbot.db.entity.Order marketSellAll() {
        Order order = binanceService.marketSellAll();
        return orderRepoService.createOrder(order);
    }
}
