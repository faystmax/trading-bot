package com.faystmax.tradingbot.service.order;

import com.faystmax.tradingbot.db.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    List<Order> findOrdersByUserEmail(String userEmail);
}
