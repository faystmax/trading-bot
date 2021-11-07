package com.faystmax.tradingbot.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SellOrderDto extends OrderDto {
    private BigDecimal notUsedQty;
    private BuyOrderDto buyOrder;

    public SellOrderDto(final OrderDto orderDto) {
        super(orderDto);
        this.price = getRealPrice();
        this.notUsedQty = this.origQty;
    }

    public SellOrderDto(final OrderDto orderDto, final BigDecimal notUsedQty) {
        super(orderDto);
        this.price = getRealPrice();
        this.notUsedQty = notUsedQty;
    }

    public BigDecimal getIncome() {
        if (buyOrder != null && origQty != null && price != null) {
            final BigDecimal usedQty = getUsedQty();
            final BigDecimal cumulativeUsedBuyQty = usedQty.multiply(buyOrder.getRealPrice());
            if (getProfit().compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            return getProfit().divide(cumulativeUsedBuyQty, RoundingMode.HALF_EVEN);
        }
        return null;
    }

    public BigDecimal getProfit() {
        if (buyOrder != null && origQty != null && price != null) {
            final BigDecimal usedQty = getUsedQty();
            final BigDecimal cumulativeUsedSellQty = usedQty.multiply(getRealPrice());
            final BigDecimal cumulativeUsedBuyQty = usedQty.multiply(buyOrder.getRealPrice());
            return cumulativeUsedSellQty.subtract(cumulativeUsedBuyQty);
        }
        return null;
    }

    public BigDecimal getCumulativeUsedSellQty() {
        final BigDecimal usedQty = getUsedQty();
        if (usedQty != null) {
            return usedQty.multiply(getRealPrice());
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getUsedQty() {
        if (origQty != null && notUsedQty != null) {
            return origQty.subtract(notUsedQty);
        }
        return origQty;
    }
}

