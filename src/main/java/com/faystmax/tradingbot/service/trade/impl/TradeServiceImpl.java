/*
 * Copyright (c)  2020, Amosov Maxim, faystmax@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.faystmax.tradingbot.service.trade.impl;

import com.binance.api.client.domain.account.Order;
import com.faystmax.tradingbot.db.entity.Trade;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TradeServiceImpl implements TradeService {

    private final OrderRepo repo;
    private final BinanceService binanceService;

    @Override
    public List<Trade> getAllTrades() {
        return null;
    }

    @Transactional
    @Override
    public void updateDatabaseOrdersFromExchange() {
        List<Order> binanceOrders = binanceService.getAllMyOrders();
        List<com.faystmax.tradingbot.db.entity.Order> myOrders = new ArrayList<>();
        binanceOrders.forEach(order -> {
            var myOrder = new com.faystmax.tradingbot.db.entity.Order();
            myOrder.setExchangeId(order.getOrderId().toString());
            myOrder.setDateAdd(new Date(order.getTime()));
            myOrder.setPrice(new BigDecimal(order.getPrice()));
            myOrder.setStopPrice(new BigDecimal(order.getStopPrice()));
            myOrder.setOrigQty(new BigDecimal(order.getOrigQty()));
            myOrder.setExecutedQty(new BigDecimal(order.getExecutedQty()));
            myOrder.setIcebergQty(new BigDecimal(order.getIcebergQty()));
            myOrder.setStatus(order.getStatus());
            myOrder.setTimeInForce(order.getTimeInForce());
            myOrder.setType(order.getType());
            myOrder.setSide(order.getSide());
            myOrders.add(myOrder);
        });
        repo.saveAll(myOrders);
    }
}
