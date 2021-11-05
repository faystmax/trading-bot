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
    protected Long id;
    protected String exchangeId;
    protected String symbol;
    protected Date dateAdd;
    protected BigDecimal price;
    protected BigDecimal stopPrice;
    protected BigDecimal origQty;
    protected BigDecimal executedQty;
    protected BigDecimal icebergQty;
    protected BigDecimal cumulativeQuoteQty;
    protected OrderStatus status;
    protected TimeInForce timeInForce;
    protected OrderType type;
    protected OrderSide side;
    protected Date dateUpdate;

    public BigDecimal getRealPrice() {
        return BigDecimal.ZERO.compareTo(price) == 0 ? (cumulativeQuoteQty.divide(executedQty, RoundingMode.HALF_EVEN)) : price;
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
    }
}
