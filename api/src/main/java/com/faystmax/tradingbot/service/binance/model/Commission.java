package com.faystmax.tradingbot.service.binance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Amosov Maxim
 * @since 05.10.2021 : 22:40
 */
@Data
@AllArgsConstructor
public class Commission {
    private BigDecimal makerCommission;
    private BigDecimal takerCommission;

    public static Commission of(final int makerCommission, final int takerCommission, final BigDecimal baseCommission) {
        return new Commission(
            baseCommission.multiply(new BigDecimal(makerCommission)),
            baseCommission.multiply(new BigDecimal(takerCommission))
        );
    }
}
