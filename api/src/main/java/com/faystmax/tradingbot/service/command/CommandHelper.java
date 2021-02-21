package com.faystmax.tradingbot.service.command;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.util.DateUtils;

import java.util.Date;

public final class CommandHelper {
    /**
     * Return order completed message
     *
     * @param order user order
     * @return order completed message
     */
    public static String getOrderCompletedMsg(Order order) {
        return order.getSide() + " completed!" +
            " price = " + order.getPrice().toPlainString() +
            ", date = " + DateUtils.format(new Date());
    }
}
