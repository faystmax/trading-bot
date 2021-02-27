package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.exception.ServiceException;
import com.faystmax.tradingbot.service.mail.MailIdleFactory;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailIdleServiceImpl implements MailIdleService {
    private final MailIdleFactory mailIdleFactory;
    private final Map<User, ImapIdleChannelAdapter> channelByUserMap = new HashMap<>();
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    @PostConstruct
    public void init() {
        taskScheduler.setPoolSize(4);
        taskScheduler.afterPropertiesSet();
    }

    @Override
    public void createIdle(User user) {
        ImapIdleChannelAdapter channelAdapter = mailIdleFactory.createIdleChannelAdapter(user, taskScheduler);
        if (channelByUserMap.containsKey(user)) {
            throw new ServiceException("Mail Idle already exist for user = " + user);
        }
        channelByUserMap.put(user, channelAdapter);
        log.info("Create mail Idle fo user = " + user);
    }

    @Override
    public void startIdle(User user) {
        if (!channelByUserMap.containsKey(user)) {
            createIdle(user);
        }
        channelByUserMap.get(user).start();
        log.info("Start mail Idle fo user = " + user);
    }

    @Override
    public void stopIdle(User user) {
        if (channelByUserMap.containsKey(user)) {
            channelByUserMap.get(user).stop();
        }
        log.info("Start mail Idle fo user = " + user);
    }

    @Override
    public void reCreateIdle(User user) {
        ImapIdleChannelAdapter channelAdapter = mailIdleFactory.createIdleChannelAdapter(user, taskScheduler);
        channelByUserMap.put(user, channelAdapter);
        log.info("Recreate mail Idle fo user = " + user);
    }
}
