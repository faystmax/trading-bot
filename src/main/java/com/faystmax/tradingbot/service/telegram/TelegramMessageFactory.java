package com.faystmax.tradingbot.service.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class TelegramMessageFactory {
    public SendMessage createMsg(final long chatId, final String text) {
        SendMessage message = new SendMessage(chatId, text);
        message.enableHtml(true);
        return message;
    }
}
