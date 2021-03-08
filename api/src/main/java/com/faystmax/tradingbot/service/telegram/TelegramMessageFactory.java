package com.faystmax.tradingbot.service.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class TelegramMessageFactory {
    public SendMessage createMsg(final long chatId,
                                 final String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.enableHtml(true);
        return message;
    }

    public SendMessage createMsg(final long chatId,
                                 final String text,
                                 final ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.enableHtml(true);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }
}
