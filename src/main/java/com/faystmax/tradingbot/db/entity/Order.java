package com.faystmax.tradingbot.db.entity;

import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderStatus;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 7311476442050005309L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exchangeId;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;

    @Basic(optional = false)
    private BigDecimal price;

    private BigDecimal stopPrice;

    @Basic(optional = false)
    private BigDecimal origQty;

    @Basic(optional = false)
    private BigDecimal executedQty;

    private BigDecimal icebergQty;

    private BigDecimal cummulativeQuoteQty;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private TimeInForce timeInForce;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    private Long transactTime;

    @OneToMany(mappedBy = "order")
    private List<Trade> trades;
}


