package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.service.binance.Balance;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Displays current balance of selected symbol
 *
 * @see com.faystmax.tradingbot.config.BinanceConfig#getSymbol()
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BalanceCommand implements Command {
    private final static String BALANCE_CODE = "balance";
    private final static String BALANCE_DESCRIPTION = "balance.description";

    private final MessageSource messageSource;
    private final BinanceService binanceService;

    @Override
    public String getCode() {
        return BALANCE_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(BALANCE_DESCRIPTION);
    }

    @Override
    public String execute(Collection<String> args) {
        Pair<Balance, Balance> balancePair = binanceService.getCurrentBalance();
        var builder = new StringBuilder();
        appendBalance(builder, balancePair.getLeft());
        builder.append("\n");
        appendBalance(builder, balancePair.getRight());
        return builder.toString();
    }

    /**
     * Appends balance data to builder
     *
     * @param builder - builder where data will be appended
     * @param balance - balance data to append
     */
    private void appendBalance(final StringBuilder builder, final Balance balance) {
        builder.append(balance.getAsset()).append(":").append("\n")
            .append("    Free = <b>").append(balance.getFree().toPlainString()).append("</b>\n")
            .append("    Locked = <b>").append(balance.getLocked().toPlainString()).append("</b>\n")
            .append("    All = <b>").append(balance.getAll().toPlainString()).append("</b>\n");
    }
}
