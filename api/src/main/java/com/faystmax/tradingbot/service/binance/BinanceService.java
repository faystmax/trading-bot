package com.faystmax.tradingbot.service.binance;

import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.binance.api.client.domain.trade.Order;
import com.faystmax.binance.api.client.domain.trade.Trade;

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
    FullBalance getCurrentBalance();

    /**
     * Buy at market price on all free balance
     *
     * @return order response
     */
    NewOrderResponse marketBuyAll();

    /**
     * Buy at market price
     *
     * @param quantity amount you want to buy
     * @return order response
     */
    NewOrderResponse marketBuy(BigDecimal quantity);

    /**
     * Buy at market price by quoteQuantity
     *
     * @param quoteQuantity specifies the amount you want to spend
     * @return order response
     */
    NewOrderResponse marketBuyQuoteQty(BigDecimal quoteQuantity);

    /**
     * Sell at market price on all free balance
     *
     * @return order response
     */
    NewOrderResponse marketSellAll();

    /**
     * Sell at market price
     *
     * @param quantity amount you want to sell
     * @return order response
     */
    NewOrderResponse marketSell(BigDecimal quantity);
}
