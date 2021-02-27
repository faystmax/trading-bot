package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Profile("master")
@RequiredArgsConstructor
public class MailIdleStartUpService implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepo repo;
    private final MailIdleService mailIdleService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Starting mailIdle...");
        List<User> users = repo.findAll();
        int mailIdleCount = 0;
        for (User user : users) {
            try {
                mailIdleService.createIdle(user);
                mailIdleService.startIdle(user);
                mailIdleCount++;
            } catch (Exception ex) {
                log.error("Can't start mail Idle for user = " + user, ex);
            }
        }
        log.info("Ending mailIdle! mailIdleCount = " + mailIdleCount);
    }
}
