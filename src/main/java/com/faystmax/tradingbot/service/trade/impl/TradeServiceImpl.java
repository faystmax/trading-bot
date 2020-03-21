package com.faystmax.tradingbot.service.trade.impl;

import com.binance.api.client.domain.account.Order;
import com.faystmax.tradingbot.db.entity.Trade;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.repo.OrderRepoService;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TradeServiceImpl implements TradeService {
    private final BinanceService binanceService;
    private final OrderRepoService orderRepoService;

    @Override
    public List<Trade> getAllTrades() {
        return null;
    }

    @Override
    @Transactional
    public com.faystmax.tradingbot.db.entity.Order marketBuyAll() {
        Order order = binanceService.marketBuyAll();
        return orderRepoService.createOrder(order);
    }

    @Transactional
    @Override
    public void updateDatabaseOrdersFromExchange() {
        List<Order> binanceOrders = binanceService.getAllMyOrders();
        binanceOrders.forEach(orderRepoService::createOrder);
    }
}
