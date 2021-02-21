package com.faystmax.tradingbot.service.order;

import com.faystmax.tradingbot.db.entity.Order;

import java.util.List;

public interface OrderService {
    /**
     * Return all orders
     *
     * @return all orders
     */
    List<Order> findAllOrders();

    /**
     * Return all user orders by user email
     *
     * @param userEmail user email
     * @return all user orders
     */
    List<Order> findOrdersByUserEmail(String userEmail);
}
