package com.faystmax.tradingbot.service.order;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;

import java.util.List;

public interface OrderService {
    /**
     * Return all user orders by user email
     *
     * @param userEmail user email
     * @return all user orders
     */
    List<Order> findOrdersByUserEmail(String userEmail);

    /**
     * Return all user orders by user
     *
     * @param user user
     * @return all user orders
     */
    List<Order> findOrdersByUser(final User user);
}
