package com.faystmax.tradingbot.service.command.impl;

import com.binance.api.client.domain.account.NewOrderResponse;
import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.service.binance.Balance;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BuyMarketCommand implements Command {
    private static final String BUY_MARKET_CODE = "buyMarket";
    private static final String BUY_MARKET_DESCRIPTION = "buyMarket.description";

    private final MessageSource messageSource;
    private final BinanceService binanceService;

    @Override
    public String getCode() {
        return BUY_MARKET_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(BUY_MARKET_DESCRIPTION, binanceService.getTradingSymbol());
    }

    @Override
    public String execute(Collection<String> args) {
        Pair<Balance, Balance> currentBalance = binanceService.getCurrentBalance();
        NewOrderResponse orderResponse = binanceService.marketBuy(currentBalance.getLeft().getFree());
        // TODO: 22.02.2020
        return "Order = " + orderResponse.toString();
    }
}
