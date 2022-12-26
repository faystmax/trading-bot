package com.faystmax.tradingbot.db.entity;

import com.faystmax.binance.api.client.domain.enums.OrderType;
import com.faystmax.binance.api.client.domain.trade.OrderSide;
import com.faystmax.binance.api.client.domain.trade.OrderStatus;
import com.faystmax.binance.api.client.domain.trade.TimeInForce;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 7311476442050005309L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exchangeId;

    private String symbol;

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

    private Boolean working;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;

    @JoinColumn(name = "userRef", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
}


