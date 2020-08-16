package com.faystmax.tradingbot.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailMessageHandler implements MessageHandler {
    @Override
    @SneakyThrows
    public void handleMessage(Message<?> message) throws MessagingException {
        if (message.getPayload() instanceof MimeMessage) {
            log.info("Message: " + new MimeMessageParser(((MimeMessage) message.getPayload())).parse().getPlainContent());
        }
    }
}
