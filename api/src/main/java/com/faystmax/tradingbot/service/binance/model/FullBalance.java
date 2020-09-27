package com.faystmax.tradingbot.service.binance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Wrapper for Base and Quote balance
 */
@Data
@AllArgsConstructor
public class FullBalance {
    private final Balance base;
    private final Balance quote;

    public BigDecimal getAll() {
        return base.getAll().add(quote.getAll());
    }
}
