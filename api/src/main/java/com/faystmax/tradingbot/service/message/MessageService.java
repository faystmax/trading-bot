package com.faystmax.tradingbot.service.message;

import com.faystmax.tradingbot.db.entity.User;

public interface MessageService {
    /**
     * Send message to owner
     *
     * @param message text to send
     */
    void sendMessageToOwner(String message);

    /**
     * Send message to user
     *
     * @param user user
     * @param message text to send
     */
    void sendMessageToUser(User user, String message);
}
