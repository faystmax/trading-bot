package com.faystmax.tradingbot.service.mail;

import com.faystmax.tradingbot.db.entity.User;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public interface MailIdleFactory {
    /**
     * Create mail ImapIdleChannelAdapter for specified user
     *
     * @param user db user
     * @param taskScheduler task scheduler
     * @return channel adapter
     */
    ImapIdleChannelAdapter createIdleChannelAdapter(User user, ThreadPoolTaskScheduler taskScheduler);
}
