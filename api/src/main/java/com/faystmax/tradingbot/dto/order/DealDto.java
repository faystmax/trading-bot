package com.faystmax.tradingbot.dto.order;

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

    private BuyOrderDto buyOrder;
    private Date lastSellDate;
    private Boolean isFilled;
    private BigDecimal dealIncome;
    private BigDecimal dealProfit;
    private List<SellOrderDto> sellOrders;

    public DealDto(final BuyOrderDto buyOrder, final List<SellOrderDto> sellOrders) {
        this.buyOrder = buyOrder;
        this.buyId = buyOrder.getId();
        this.symbol = buyOrder.getSymbol();
        this.buyPrice = buyOrder.getPrice();
        this.buyDate = buyOrder.getDateAdd();
        this.buyQty = buyOrder.getQtyWithoutCommission();
        this.buyCumulativeQty = buyOrder.getCumulativeQtyWithoutCommission();
        this.isFilled = buyOrder.getIsFullySold();
        this.sellOrders = sellOrders;

        if (CollectionUtils.isNotEmpty(sellOrders)) {
            this.lastSellDate = Iterables.getLast(sellOrders).getDateUpdate();
            this.sellOrders.forEach(sellOrder -> sellOrder.setBuyOrder(buyOrder));
            if (isFilled) {
                this.dealProfit = this.sellOrders.stream()
                    .map(SellOrderDto::getProfit)
                    .reduce(ZERO, BigDecimal::add);

                if (this.dealProfit.compareTo(ZERO) == 0) {
                    this.dealIncome = ZERO;
                } else {
                    this.dealIncome = dealProfit.divide(buyCumulativeQty, RoundingMode.HALF_EVEN);
                }
            }
        }
    }
}
