package com.faystmax.tradingbot.service.trade.impl;

import com.binance.api.client.domain.account.Order;
import com.faystmax.tradingbot.db.entity.Trade;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TradeServiceImpl implements TradeService {

    private final OrderRepo repo;
    private final BinanceService binanceService;

    @Override
    public List<Trade> getAllTrades() {
        return null;
    }

    @Transactional
    @Override
    public void updateDatabaseOrdersFromExchange() {
        List<Order> binanceOrders = binanceService.getAllMyOrders();
        List<com.faystmax.tradingbot.db.entity.Order> myOrders = new ArrayList<>();
        binanceOrders.forEach(order -> {
            var myOrder = new com.faystmax.tradingbot.db.entity.Order();
            myOrder.setExchangeId(order.getOrderId().toString());
            myOrder.setDateAdd(new Date(order.getTime()));
            myOrder.setPrice(new BigDecimal(order.getPrice()));
            myOrder.setStopPrice(new BigDecimal(order.getStopPrice()));
            myOrder.setOrigQty(new BigDecimal(order.getOrigQty()));
            myOrder.setExecutedQty(new BigDecimal(order.getExecutedQty()));
            myOrder.setIcebergQty(new BigDecimal(order.getIcebergQty()));
            myOrder.setStatus(order.getStatus());
            myOrder.setTimeInForce(order.getTimeInForce());
            myOrder.setType(order.getType());
            myOrder.setSide(order.getSide());
            myOrders.add(myOrder);
        });
        repo.saveAll(myOrders);
    }
}
