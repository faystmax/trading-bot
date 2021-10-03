package com.faystmax.tradingbot.service.binance;

import com.faystmax.binance.api.client.domain.TickerPrice;
import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.binance.api.client.domain.trade.Order;
import com.faystmax.binance.api.client.domain.trade.Trade;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.model.FullBalance;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for interaction with binance
 */
public interface BinanceService {
    /**
     * @param user user
     * @return total USDT amount on binance account
     */
    BigDecimal getTotalUsdtAmount(User user);

    /**
     * @param user user
     * @return list of active trading symbols
     */
    List<String> getActiveSymbols(User user);

    /**
     * @param user user
     * @return last price of selected user symbol
     */
    List<TickerPrice> getAllLastPrices(User user);

    /**
     * @param user user
     * @return last price of selected user symbol
     */
    BigDecimal getLastPrice(User user, String symbol);

    /**
     * @param user user
     * @param limit maximum amount of returned trades
     * @return last trades of selected user symbol
     */
    List<Trade> getMyTrades(User user, Integer limit);

    /**
     * @param user user
     * @param symbol trading symbol
     * @return all last account orders (in last 3 months)
     */
    List<Order> getAllMyOrders(User user, String symbol);

    /**
     * Return Base and Quote balance
     * (for symbol = "ETHUSDT" it will be "ETH" and "USDT" balances)
     *
     * @param user user
     * @return base and quote balances
     */
    FullBalance getCurrentBalance(User user);

    /**
     * Buy at market price on all free balance
     *
     * @param user user
     * @return order response
     */
    NewOrderResponse marketBuyAll(User user);

    /**
     * Buy at market price
     *
     * @param user user
     * @param quantity amount you want to buy
     * @return order response
     */
    NewOrderResponse marketBuy(User user, BigDecimal quantity);

    /**
     * Buy at market price by quoteQuantity
     *
     * @param user user
     * @param quoteQuantity specifies the amount you want to spend
     * @return order response
     */
    NewOrderResponse marketBuyQuoteQty(User user, BigDecimal quoteQuantity);

    /**
     * Sell at market price on all free balance
     *
     * @param user user
     * @return order response
     */
    NewOrderResponse marketSellAll(User user);

    /**
     * Sell at market price
     *
     * @param user user
     * @param quantity amount you want to sell
     * @return order response
     */
    NewOrderResponse marketSell(User user, BigDecimal quantity);
}
