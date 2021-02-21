package com.faystmax.tradingbot.service.mail;

import com.faystmax.tradingbot.db.entity.User;

/**
 * Service for working with mailIdle listeners
 */
public interface MailIdleService {
    /**
     * Create Mail Idle for user
     *
     * @param user db user
     */
    void createIdle(User user);

    /**
     * Start Mail Idle for user
     *
     * @param user db user
     */
    void startIdle(User user);

    /**
     * Stop Mail Idle for user
     *
     * @param user db user
     */
    void stopIdle(User user);

    /**
     * ReCreate Mail Idle for user
     *
     * @param user db user
     */
    void reCreateIdle(User user);
}
