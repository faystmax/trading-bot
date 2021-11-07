package com.faystmax.tradingbot.util;

import com.faystmax.tradingbot.service.binance.model.Commission;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class CommissionUtils {
    public BigDecimal calcQtyWithoutCommission(final Commission commission, final BigDecimal origQty) {
        return origQty.subtract(origQty.multiply(commission.getMakerCommission()));
    }
}
