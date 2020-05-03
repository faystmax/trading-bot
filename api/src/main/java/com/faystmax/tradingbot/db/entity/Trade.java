package com.faystmax.tradingbot.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade implements Serializable {
    private static final long serialVersionUID = -5510892893853844461L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String exchangeTradeId;

    @JoinColumn(name = "orderRef", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;

    @Basic(optional = false)
    private BigDecimal price;

    @Basic(optional = false)
    private BigDecimal qty;

    @Basic(optional = false)
    private BigDecimal quoteQty;

    @Basic(optional = false)
    private BigDecimal commission;

    @Basic(optional = false)
    private BigDecimal commissionAsset;

    @Basic(optional = false)
    private String symbol;

    @Basic(optional = false)
    private Boolean buyer;

    @Basic(optional = false)
    private Boolean maker;

    @Basic(optional = false)
    private Boolean bestMatch;
}
