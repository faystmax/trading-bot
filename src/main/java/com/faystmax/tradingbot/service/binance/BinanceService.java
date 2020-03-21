package com.faystmax.tradingbot.service.binance;

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
     * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
     */
    List<Trade> getMyTrades();

    /**
     * @return all last account orders (in last 3 months)
     */
    List<Order> getAllMyOrders();

    /**
     * Return Base and Quote balance
     * (for symbol = "ETHUSDT" it will be "ETH" and "USDT" balances)
     *
     * @return base and quote balances
     */
    Pair<Balance, Balance> getCurrentBalance();

    /**
     * Buy at market price on all free balance
     *
     * @return order response
     */
    Order marketBuyAll();

    /**
     * Buy on market price
     *
     * @return order response
     */
    Order marketBuy(BigDecimal quantity);
}
