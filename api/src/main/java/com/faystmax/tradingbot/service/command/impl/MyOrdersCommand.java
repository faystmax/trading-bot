package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyOrdersCommand implements Command {
    private static final String MY_ORDERS_CODE = "MyOrders";
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
        List<Order> orders = orderRepo.findFirst3ByOrderByDateAddAsc();
        if (orders.isEmpty()) {
            return messageSource.getMsg(MY_ORDERS_EMPTY);
        }

        var builder = new StringBuilder();
        builder.append("My orders ").append(":\n");
        orders.forEach(order -> {
            builder.append("Id = <b>").append(order.getId()).append("</b>\n");
            builder.append("ExchangeId = <b>").append(order.getExchangeId()).append("</b>\n");
            builder.append("Type = <b>").append(order.getType()).append("</b>\n");
            builder.append("Price = <b>").append(order.getPrice().toPlainString()).append("</b>\n");
            builder.append("OriginQty = <b>").append(order.getOrigQty().toPlainString()).append("</b>\n");
            builder.append("ExecutedQty = <b>").append(order.getExecutedQty().toPlainString()).append("</b>\n");
            builder.append("Time = <b>").append(DateUtils.format(order.getDateAdd())).append("</b>\n");
            builder.append("--------------------------------------------------\n");
        });
        return builder.toString();
    }
}
