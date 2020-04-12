package com.faystmax.tradingbot.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class TelegramMessageFactory {
    public static SendMessage createMsg(final long chatId,
                                        final String text) {
        SendMessage message = new SendMessage(chatId, text);
        message.enableHtml(true);
        return message;
    }

    public static SendMessage createMsg(final long chatId,
                                        final String text,
                                        final ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage message = new SendMessage(chatId, text);
        message.enableHtml(true);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }
}
