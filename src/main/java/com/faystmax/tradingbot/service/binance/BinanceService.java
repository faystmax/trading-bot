package com.faystmax.tradingbot.service.binance;

import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.Trade;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for interaction with binance
 */
public interface BinanceService {
    /**
     * Test connection to binance
     */
    void ping();

    /**
     * @return main trading symbol
     * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
     */
    String getTradingSymbol();

    /**
     * @return last price of selected symbol
     * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
     */
    BigDecimal getLastPrice();

    /**
     * @return last trades of selected symbol
     */
    List<Trade> getMyTrades();

    /**
     * @return all last account orders (in last 3 months)
     */
    List<Order> getAllMyOrders();

    /**
     * @param startTime Timestamp in ms
     * @param endTime   Timestamp in ms
     * @return all account orders between startTime and endTime
     */
    List<Order> getAllMyOrders(Long startTime, Long endTime);

    /**
     * Return Base and Quote balance
     * (for symbol = "ETHUSDT" it will be "ETH" and "USDT" balances)
     *
     * @return base and quote balances
     */
    Pair<Balance, Balance> getCurrentBalance();

    /**
     * Buy on market price
     *
     * @return order response
     */
    NewOrderResponse marketBuy(BigDecimal quantity);
}
