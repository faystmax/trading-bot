package com.faystmax.tradingbot.service.order;

import com.faystmax.tradingbot.db.entity.Order;

import java.util.Collection;

public interface OrderService {
    Collection<Order> findAllOrders();
}
