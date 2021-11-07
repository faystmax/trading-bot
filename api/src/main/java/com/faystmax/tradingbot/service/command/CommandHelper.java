package com.faystmax.tradingbot.service.command;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.util.DateUtils;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class CommandHelper {
    /**
     * Return order completed message
     *
     * @param order user order
     * @return order completed message
     */
    public String getOrderCompletedMsg(final Order order) {
        return order.getSide() + " completed!" +
            " price = " + order.getPrice().toPlainString() +
            ", date = " + DateUtils.format(new Date());
    }
}
