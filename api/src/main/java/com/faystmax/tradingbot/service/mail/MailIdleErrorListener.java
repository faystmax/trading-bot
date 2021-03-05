package com.faystmax.tradingbot.service.mail;

import org.springframework.integration.mail.ImapIdleChannelAdapter.ImapIdleExceptionEvent;

public interface MailIdleErrorListener {
    void handleException(ImapIdleExceptionEvent event);
}
