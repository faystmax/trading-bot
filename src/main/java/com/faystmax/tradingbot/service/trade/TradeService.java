package com.faystmax.tradingbot.service.trade;

import com.faystmax.tradingbot.db.entity.Order;

/**
 * Main Trade service
 */
public interface TradeService {
    /**
     * Get orders from Exchange and add it to DB
     */
    void updateDatabaseOrdersFromExchange();

    /**
     * Buy on market price
     *
     * @return created Order
     */
    Order marketBuyAll();
}
