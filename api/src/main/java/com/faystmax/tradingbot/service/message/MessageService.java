package com.faystmax.tradingbot.service.message;

public interface MessageService {
    /**
     * Send message to owner
     *
     * @param message text to send
     */
    void sentMessageToOwner(String message);
}
