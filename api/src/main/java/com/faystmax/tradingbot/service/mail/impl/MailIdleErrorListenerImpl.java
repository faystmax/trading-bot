package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.mail.MailIdleErrorListener;
import com.faystmax.tradingbot.service.mail.MailIdleErrorStore;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapIdleChannelAdapter.ImapIdleExceptionEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailIdleErrorListenerImpl implements MailIdleErrorListener {
    private final MailIdleService mailIdleService;
    private final MailIdleErrorStore mailIdleErrorStore;

    @Override
    @EventListener
    public void handleException(ImapIdleExceptionEvent event) {
        User user = mailIdleService.findUserByChannelAdapter((ImapIdleChannelAdapter) event.getSource());
        mailIdleErrorStore.put(user, event.getCause().getCause());
        log.error("Mail idle error for user = '{}', error = '{}'", user.getEmail(), event.getCause().getCause().getMessage());
    }
}
