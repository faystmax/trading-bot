/*
 * Copyright (c)  2020, Amosov Maxim, faystmax@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.component.MessageSource;
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
