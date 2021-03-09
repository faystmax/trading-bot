package com.faystmax.tradingbot.service.telegram;

public interface TelegramBot {
    /**
     * Send message to specified chat
     *
     * @param chatId user chat id
     * @param text text of the message
     */
    void sendMsg(long chatId, String text);

    /**
     * Send message to owner of the telegramBot
     *
     * @param text text of the message
     */
    void sendMsgToOwner(String text);
}
