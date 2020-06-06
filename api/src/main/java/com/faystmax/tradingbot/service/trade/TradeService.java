package com.faystmax.tradingbot.service.trade;

import com.faystmax.tradingbot.db.entity.Order;

/**
 * Main Trade service
 */
public interface TradeService {
    /**
     * Buy at market price
     *
     * @return created Order
     */
    Order marketBuyAll();

    /**
     * Sell at market price
     *
     * @return created Order
     */
    Order marketSellAll();
}
