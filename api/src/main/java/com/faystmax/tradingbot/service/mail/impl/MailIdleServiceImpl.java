package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.exception.ServiceException;
import com.faystmax.tradingbot.service.mail.MailIdleFactory;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailIdleServiceImpl implements MailIdleService {
    private final MailIdleFactory mailIdleFactory;
    private final ApplicationEventPublisher eventPublisher;
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private final Map<User, ImapIdleChannelAdapter> channelByUserMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        taskScheduler.setPoolSize(4);
        taskScheduler.afterPropertiesSet();
    }

    @Override
    public void createIdle(User user) {
        if (channelByUserMap.containsKey(user)) {
            throw new ServiceException("Mail Idle already exist for user = " + user);
        }
        ImapIdleChannelAdapter channelAdapter = mailIdleFactory.createIdleChannelAdapter(user, taskScheduler);
        channelAdapter.setApplicationEventPublisher(eventPublisher);
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
        log.info("Stop mail Idle fo user = " + user);
    }

    @Override
    public void reCreateAndStartIdle(User user) {
        checkEmailFields(user);
        if (channelByUserMap.containsKey(user)) {
            channelByUserMap.get(user).stop();
            channelByUserMap.get(user).destroy();
            channelByUserMap.remove(user);
        }
        ImapIdleChannelAdapter channelAdapter = mailIdleFactory.createIdleChannelAdapter(user, taskScheduler);
        channelAdapter.setApplicationEventPublisher(eventPublisher);
        channelByUserMap.put(user, channelAdapter);
        channelByUserMap.get(user).start();
        log.info("Recreate mail Idle for user = " + user);
    }

    @Override
    public User findUserByChannelAdapter(ImapIdleChannelAdapter channelAdapter) {
        return channelByUserMap.entrySet().stream()
            .filter(e -> e.getValue().equals(channelAdapter))
            .map(Map.Entry::getKey)
            .findFirst().orElseThrow(() -> new ServiceException("Can't find owner of channel adapter!"));
    }

    private void checkEmailFields(final User user) {
        if (StringUtils.isEmpty(user.getEmailHost())) {
            throw new ServiceException("EmailHost is empty! user = " + user.getEmail());
        } else if (StringUtils.isEmpty(user.getEmailUsername())) {
            throw new ServiceException("EmailUsername is empty! user = " + user.getEmail());
        } else if (StringUtils.isEmpty(user.getEmailPassword())) {
            throw new ServiceException("EmailPassword is empty! user = " + user.getEmail());
        } else if (StringUtils.isEmpty(user.getEmailFolder())) {
            throw new ServiceException("EmailFolder is empty! user = " + user.getEmail());
        }
    }
}
