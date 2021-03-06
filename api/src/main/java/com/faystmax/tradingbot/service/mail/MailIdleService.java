package com.faystmax.tradingbot.service.mail;

import com.faystmax.tradingbot.db.entity.User;
import org.springframework.integration.mail.ImapIdleChannelAdapter;

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
    void reCreateAndStartIdle(User user);

    /**
     * Return owner of channelAdapter
     *
     * @param channelAdapter mail channelAdapter
     * @return owner of this channelAdapter
     */
    User findUserByChannelAdapter(ImapIdleChannelAdapter channelAdapter);
}
