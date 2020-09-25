package com.faystmax.tradingbot.db.entity;

import com.faystmax.binance.api.client.domain.enums.OrderType;
import com.faystmax.binance.api.client.domain.trade.OrderSide;
import com.faystmax.binance.api.client.domain.trade.OrderStatus;
import com.faystmax.binance.api.client.domain.trade.TimeInForce;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    private BigDecimal cumulativeQuoteQty;

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


