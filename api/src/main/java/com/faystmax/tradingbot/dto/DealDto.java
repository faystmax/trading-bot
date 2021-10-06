package com.faystmax.tradingbot.dto;

import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ZERO;

/**
 * @author Amosov Maxim
 * @since 03.10.2021 : 21:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealDto {
    private String symbol;

    private Long buyId;
    private Date buyDate;
    private BigDecimal buyPrice;
    private BigDecimal buyQty;
    private BigDecimal buyCumulativeQty;

    private OrderDto buyOrder;
    private Date lastSellDate;
    private Boolean isFilled;
    private BigDecimal dealIncome;
    private BigDecimal dealProfit;
    private List<OrderDto> sellOrders;

    public DealDto(final OrderDto buyOrder, final Boolean isFilled, final List<OrderDto> sellOrders) {
        this.buyOrder = buyOrder;
        this.buyId = buyOrder.getId();
        this.symbol = buyOrder.getSymbol();
        this.buyPrice = buyOrder.getRealPrice();
        this.buyDate = buyOrder.getDateAdd();
        this.buyQty = buyOrder.getOrigQty();
        this.buyCumulativeQty = buyOrder.getCumulativeQuoteQty();
        this.isFilled = isFilled;
        this.sellOrders = sellOrders;

        if (CollectionUtils.isNotEmpty(sellOrders)) {
            this.lastSellDate = Iterables.getLast(sellOrders).getDateUpdate();
            this.sellOrders.forEach(sellOrder -> sellOrder.setBuyOrder(buyOrder));
            if (isFilled) {
                this.dealProfit = this.sellOrders.stream().map(OrderDto::getDealProfit).reduce(ZERO, BigDecimal::add);
                this.dealIncome = dealProfit.divide(buyCumulativeQty, RoundingMode.HALF_DOWN);
            }
        }
    }
}
