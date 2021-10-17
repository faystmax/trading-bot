package com.faystmax.tradingbot.service.trade;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;

/**
 * Main Trade service
 */
public interface TradeService {
    /**
     * Buy at market price
     *
     * @param user user
     * @return created Order
     */
    Order marketBuyAll(User user);

    /**
     * Sell at market price
     *
     * @param user user
     * @return created Order
     */
    Order marketSellAll(User user);

    /**
     * Sell at market price
     *
     * @param user user
     * @param symbol symbol
     * @return created Order
     */
    Order marketSellAll(final User user, final String symbol);
}
