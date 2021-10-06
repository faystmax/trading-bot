package com.faystmax.tradingbot.service.binance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Amosov Maxim
 * @since 05.10.2021 : 22:40
 */
@Data
@AllArgsConstructor
public class Commission {
    private int makerCommission;
    private int takerCommission;
}
