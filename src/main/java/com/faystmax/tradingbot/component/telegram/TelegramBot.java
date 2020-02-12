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

package com.faystmax.tradingbot.component.telegram;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.config.TelegramConfig;
import com.faystmax.tradingbot.service.binance.BinanceService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    public static final String MESSAGE_FROM_STRANGER = "telegram.bot.message.from.stranger";

    private final TelegramConfig config;
    private final BinanceService binanceService;
    private final MessageSource messageSource;

    @Autowired
    public TelegramBot(DefaultBotOptions options,
                       TelegramConfig config,
                       BinanceService binanceService,
                       MessageSource messageSource) {
        super(options);
        this.config = config;
        this.binanceService = binanceService;
        this.messageSource = messageSource;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    public void sendMsg(final String text) {
        log.info("Sending message {} to chat = {} ", text, config.getChatId());
        this.sendApiMethod(new SendMessage(config.getChatId(), text));
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(final Update update) {
        final Message msg = update.getMessage();
        if (!Objects.equals(msg.getChatId(), config.getChatId())) {
            final String message = messageSource.getMsg(MESSAGE_FROM_STRANGER, msg.getText(),
                msg.getContact().getUserID(),
                msg.getChatId()
            );
            log.info(message);
            this.sendApiMethod(new SendMessage(msg.getChatId(), message));
        }

        this.sendApiMethod(new SendMessage(msg.getChatId(), "Hello Admin! ETH price = " + binanceService.getLastPrice()));
    }
}