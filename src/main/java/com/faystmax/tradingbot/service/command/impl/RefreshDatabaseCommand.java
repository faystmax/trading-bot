package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RefreshDatabaseCommand implements Command {
    private static final String REFRESH_DATABASE_CODE = "refreshDatabase";
    private static final String REFRESH_DATABASE_DESCRIPTION = "refreshDatabase.description";
    private static final String REFRESH_DATABASE_COMPLETE = "refreshDatabase.complete";

    private final TradeService tradeService;
    private final MessageSource messageSource;

    @Override
    public String getCode() {
        return REFRESH_DATABASE_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(REFRESH_DATABASE_DESCRIPTION);
    }

    @Override
    public String execute(Collection<String> args) {
        tradeService.updateDatabaseOrdersFromExchange();
        return messageSource.getMsg(REFRESH_DATABASE_COMPLETE);
    }
}
