package com.faystmax.tradingbot.service.order;

import com.faystmax.tradingbot.db.entity.User;

/**
 * @author Amosov Maxim
 * @since 30.09.2021 : 23:07
 */
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
