package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.exception.MailCommandTranslateException;
import com.faystmax.tradingbot.service.command.CommandExecutor;
import com.faystmax.tradingbot.service.command.impl.BuyMarketCommand;
import com.faystmax.tradingbot.service.command.impl.SellMarketCommand;
import com.faystmax.tradingbot.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.mail.internet.MimeMessage;

import static org.apache.commons.lang3.StringUtils.*;

@Slf4j
@RequiredArgsConstructor
public class MimeMessageHandler implements MessageHandler {
    private final User user;
    private final MessageService messageService;
    private final CommandExecutor commandExecutor;

    @Override
    @SneakyThrows
    public void handleMessage(final Message<?> message) throws MessagingException {
        if (message.getPayload() instanceof MimeMessage) {
            log.info("User {} Received message: {}", user.getEmail(), message);
            final MimeMessageParser parser = new MimeMessageParser(((MimeMessage) message.getPayload())).parse();

            final String content = getMainContent(parser);
            log.info("Message content: " + content);

            final String mainText = substringBetween(content, "!START!", "!END!");
            if (isNotBlank(mainText)) {
                final String result = commandExecutor.execute(user, translateToCommandCode(mainText));
                messageService.sendMessageToUser(user, result);
            }
        }
    }

    private String getMainContent(final MimeMessageParser parser) {
        return firstNonEmpty(parser.getPlainContent(), parser.getHtmlContent(), EMPTY);
    }

    private String translateToCommandCode(final String mainText) {
        if (mainText.contains("buy")) {
            return BuyMarketCommand.BUY_MARKET_CODE;
        } else if (mainText.contains("sell")) {
            return SellMarketCommand.SELL_MARKET_CODE;
        }
        throw new MailCommandTranslateException(mainText);
    }
}
