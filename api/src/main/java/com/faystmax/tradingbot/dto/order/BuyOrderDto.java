package com.faystmax.tradingbot.dto.order;

import com.faystmax.tradingbot.dto.OrderDto;
import com.faystmax.tradingbot.service.binance.model.Commission;
import com.faystmax.tradingbot.util.CommissionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Amosov Maxim
 * @since 05.11.2021 : 12:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BuyOrderDto extends OrderDto {
    private Boolean isFullySold;
    private BigDecimal notSoldQty;
    private BigDecimal qtyWithoutCommission;
    private BigDecimal cumulativeQtyWithoutCommission;

    public BuyOrderDto(final OrderDto orderDto, final Commission commission) {
        super(orderDto);
        this.price = getRealPrice();
        this.notSoldQty = this.origQty;
        this.qtyWithoutCommission = CommissionUtils.calcQtyWithoutCommission(commission, this.origQty);
        this.cumulativeQtyWithoutCommission = qtyWithoutCommission.multiply(price);
    }
}

