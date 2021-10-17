package com.faystmax.tradingbot.service.trade.impl;

import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;
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

    @Override
    @Transactional
    public Order marketBuyAll(final User user) {
        final NewOrderResponse response = binanceService.marketBuyAll(user);
        return orderRepoService.createOrder(user, response);
    }

    @Override
    @Transactional
    public Order marketSellAll(final User user) {
        final NewOrderResponse response = binanceService.marketSellAll(user);
        return orderRepoService.createOrder(user, response);
    }

    @Override
    @Transactional
    public Order marketSellAll(final User user, final String symbol) {
        final NewOrderResponse response = binanceService.marketSellAll(user, symbol);
        return orderRepoService.createOrder(user, response);
    }
}
