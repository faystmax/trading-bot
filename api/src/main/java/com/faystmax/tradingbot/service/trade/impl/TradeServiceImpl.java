package com.faystmax.tradingbot.service.trade.impl;

import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.repo.OrderRepoService;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final BinanceService binanceService;
    private final OrderRepoService orderRepoService;

    @Transactional
    @Override
    public Order marketBuyAll() {
        NewOrderResponse response = binanceService.marketBuyAll();
        return orderRepoService.createOrder(response);
    }

    @Transactional
    @Override
    public Order marketSellAll() {
        NewOrderResponse response = binanceService.marketSellAll();
        return orderRepoService.createOrder(response);
    }
}
