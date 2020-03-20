package com.faystmax.tradingbot.service.trade;

import com.binance.api.client.domain.account.NewOrderResponse;
import com.faystmax.tradingbot.db.entity.Trade;

import java.util.List;

/**
 * Main Trade service
 */
public interface TradeService {
    /**
     * Get orders from Exchange and add it to DB
     */
    void updateDatabaseOrdersFromExchange();

    /**
     * @return all trades
     */
    List<Trade> getAllTrades();

    /**
     * Buy on market price
     *
     * @return response
     */
    NewOrderResponse marketBuyAll();
}