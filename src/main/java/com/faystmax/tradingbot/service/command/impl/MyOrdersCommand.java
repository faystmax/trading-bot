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

package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MyOrdersCommand implements Command {
    private static final String MY_ORDERS_CODE = "myOrders";
    private static final String MY_ORDERS_DESCRIPTION = "myOrders.description";
    private static final String MY_ORDERS_EMPTY = "myOrders.empty";

    private final OrderRepo orderRepo;
    private final MessageSource messageSource;

    @Override
    public String getCode() {
        return MY_ORDERS_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(MY_ORDERS_DESCRIPTION);
    }

    @Override
    public String execute(Collection<String> args) {
        List<Order> orders = orderRepo.findAllByOrderByDateAddAsc();
        if (orders.isEmpty()) {
            return messageSource.getMsg(MY_ORDERS_EMPTY);
        }

        var builder = new StringBuilder();
        builder.append("My orders \"").append("\":\n");
        orders.forEach(order -> {
            builder.append("Id = <b>").append(order.getId()).append("</b>\n");
            builder.append("ExchangeId = <b>").append(order.getExchangeId()).append("</b>\n");
            builder.append("Price = <b>").append(order.getPrice()).append("</b>\n");
            builder.append("OriginQty = <b>").append(order.getOrigQty()).append("</b>\n");
            builder.append("ExecutedQty = <b>").append(order.getExecutedQty()).append("</b>\n");
            builder.append("Time = <b>").append(DateUtils.format(order.getDateAdd())).append("</b>\n");
            builder.append("--------------------------------------------------\n");
        });
        return builder.toString();
    }
}
