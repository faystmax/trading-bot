package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
                if (checkEmailFields(user)) {
                    mailIdleService.createIdle(user);
                    mailIdleService.startIdle(user);
                    mailIdleCount++;
                }
            } catch (Exception ex) {
                log.error("Can't start mail Idle for user = " + user, ex);
            }
        }
        log.info("Ending mailIdle! mailIdleCount = " + mailIdleCount);
    }

    private boolean checkEmailFields(User user) {
        if (StringUtils.isEmpty(user.getEmailHost())) {
            log.warn("EmailHost is empty! user = {}", user.getEmail());
            return false;
        } else if (StringUtils.isEmpty(user.getEmailUsername())) {
            log.warn("EmailUsername is empty! user = {}", user.getEmail());
            return false;
        } else if (StringUtils.isEmpty(user.getEmailPassword())) {
            log.warn("EmailPassword is empty! user = {}", user.getEmail());
            return false;
        } else if (StringUtils.isEmpty(user.getEmailFolder())) {
            log.warn("EmailFolder is empty! user = {}", user.getEmail());
            return false;
        }
        return true;
    }
}
