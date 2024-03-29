package com.faystmax.tradingbot.service.binance.model;

import com.faystmax.binance.api.client.domain.account.AssetBalance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Wrapper to work with balance as BigDecimal
 */
@Getter
@Setter
@NoArgsConstructor
public class Balance {
    /**
     * Asset symbol.
     */
    private String asset;
    /**
     * Available balance.
     */
    private BigDecimal free;
    /**
     * Locked by open orders.
     */
    private BigDecimal locked;

    public BigDecimal getAll() {
        return free.add(locked);
    }

    public static Balance valueOf(final AssetBalance assetBalance) {
        final Balance balance = new Balance();
        balance.setAsset(assetBalance.getAsset());
        balance.setFree(assetBalance.getFree());
        balance.setLocked(assetBalance.getLocked());
        return balance;
    }
}
