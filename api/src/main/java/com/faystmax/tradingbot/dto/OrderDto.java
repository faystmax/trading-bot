package com.faystmax.tradingbot.dto;

import com.faystmax.binance.api.client.domain.enums.OrderType;
import com.faystmax.binance.api.client.domain.trade.OrderSide;
import com.faystmax.binance.api.client.domain.trade.OrderStatus;
import com.faystmax.binance.api.client.domain.trade.TimeInForce;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String exchangeId;
    private String symbol;
    private Date dateAdd;
    private BigDecimal price;
    private BigDecimal stopPrice;
    private BigDecimal origQty;
    private BigDecimal executedQty;
    private BigDecimal icebergQty;
    private BigDecimal cumulativeQuoteQty;
    private OrderStatus status;
    private TimeInForce timeInForce;
    private OrderType type;
    private OrderSide side;
    private Date dateUpdate;

    private BigDecimal notUsedQty;
    private transient OrderDto buyOrder;

    public BigDecimal getRealPrice() {
        return BigDecimal.ZERO.compareTo(price) == 0 ? (cumulativeQuoteQty.divide(origQty, RoundingMode.HALF_EVEN)) : price;
    }

    public void setOrigQtyWithoutCommission(final BigDecimal origQtyWithoutCommission) {
        if(BigDecimal.ZERO.compareTo(price) == 0){
            this.price = getRealPrice();
        }
        this.origQty = origQtyWithoutCommission;
        this.cumulativeQuoteQty = origQtyWithoutCommission.multiply(price);
    }

    public BigDecimal getDealIncome() {
        if (buyOrder != null && origQty != null && price != null) {
            final BigDecimal usedQty = getUsedQty();
            final BigDecimal cumulativeUsedBuyQty = usedQty.multiply(buyOrder.getRealPrice());
            if(getDealProfit().compareTo(BigDecimal.ZERO) == 0){
                return BigDecimal.ZERO;
            }
            return getDealProfit().divide(cumulativeUsedBuyQty, RoundingMode.HALF_EVEN);
        }
        return null;
    }

    public BigDecimal getDealProfit() {
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

    public OrderDto(final OrderDto source) {
        this.id = source.id;
        this.exchangeId = source.getExchangeId();
        this.symbol = source.getSymbol();
        this.dateAdd = source.getDateAdd();
        this.price = source.getPrice();
        this.stopPrice = source.getStopPrice();
        this.origQty = source.getOrigQty();
        this.executedQty = source.getExecutedQty();
        this.icebergQty = source.getIcebergQty();
        this.cumulativeQuoteQty = source.getCumulativeQuoteQty();
        this.status = source.getStatus();
        this.timeInForce = source.getTimeInForce();
        this.type = source.getType();
        this.side = source.getSide();
        this.dateUpdate = source.getDateUpdate();
        this.notUsedQty = source.getNotUsedQty();
        this.buyOrder = source.getBuyOrder();
    }
}
