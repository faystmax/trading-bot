package com.faystmax.tradingbot.util;

import com.faystmax.tradingbot.service.binance.model.Commission;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

/**
 * @author Amosov Maxim
 * @since 05.11.2021 : 16:25
 */
@UtilityClass
public class CommissionUtils {
    public BigDecimal calcQtyWithoutCommission(final Commission commission, final BigDecimal origQty) {
        return origQty.subtract(origQty.multiply(commission.getMakerCommission()));
    }
}
