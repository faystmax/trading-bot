package com.faystmax.tradingbot.service.mail;

import com.faystmax.tradingbot.db.entity.User;

public interface MailIdleErrorStore {
    /**
     * Put Error for user
     *
     * @param user db user
     * @param throwable error
     */
    void put(User user, Throwable throwable);

    /**
     * Get error message for user
     *
     * @param user db user
     * @return error message for user
     */
    String getErrorMessage(User user);

    /**
     * Clear error for user
     *
     * @param user db user
     */
    void clear(User user);
}
