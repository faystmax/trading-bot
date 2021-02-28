package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapIdleChannelAdapter.ImapIdleExceptionEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailIdleErrorListener {
    private final MailIdleService mailIdleService;
    private final Map<User, Throwable> errorByUserMap = new HashMap<>();

    @EventListener
    public void handleException(ImapIdleExceptionEvent event) {
        User user = mailIdleService.findUserByChannelAdapter((ImapIdleChannelAdapter) event.getSource());
        errorByUserMap.put(user, event.getCause().getCause());
        log.error("Mail idle error for user = '{}', error = '{}'", user.getEmail(), event.getCause().getCause().getMessage());
    }

    public String getErrorMessage(User user) {
        Throwable throwable = errorByUserMap.get(user);
        return throwable == null ? "No Errors!" : throwable.getClass() + ": " + throwable.getMessage();
    }

    public void clearErrorMap(User user) {
        errorByUserMap.put(user, null);
    }
}
