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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String exchangeId;
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
    private Long transactTime;
}
