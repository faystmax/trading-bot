package com.faystmax.tradingbot.service.order;

import com.faystmax.tradingbot.db.entity.User;

public interface OrderReloader {
    /**
     * Reload orders of all users
     */
    void reloadOrders();

    /**
     * Reload orders for user
     */
    void reloadOrdersForUser(User user);
}
