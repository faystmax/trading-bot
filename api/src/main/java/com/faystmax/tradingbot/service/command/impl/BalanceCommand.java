package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.model.Balance;
import com.faystmax.tradingbot.service.binance.model.FullBalance;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Displays current balance of selected symbol
 *
 * @see User#getTradingSymbol()
 */
@Component
@RequiredArgsConstructor
public class BalanceCommand implements Command {
    private final static String BALANCE_CODE = "MyBalance";
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
    public String execute(final User user, final Collection<String> args) {
        final FullBalance balance = binanceService.getCurrentBalance(user);
        final var builder = new StringBuilder();
        appendBalance(builder, balance.getBase());
        appendBalance(builder, balance.getQuote());
        return builder.toString();
    }

    /**
     * Appends balance data to builder
     *
     * @param builder builder where data will be appended
     * @param balance balance data to append
     */
    private void appendBalance(final StringBuilder builder, final Balance balance) {
        builder.append(balance.getAsset()).append(":").append("\n")
            .append("    Free = <b>").append(balance.getFree().toPlainString()).append("</b>\n")
            .append("    Locked = <b>").append(balance.getLocked().toPlainString()).append("</b>\n")
            .append("    All = <b>").append(balance.getAll().toPlainString()).append("</b>\n");
    }
}
