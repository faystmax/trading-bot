package com.faystmax.tradingbot.service.mail;

import com.faystmax.tradingbot.service.telegram.TelegramBot;
import com.faystmax.tradingbot.service.trade.TradeService;
import com.faystmax.tradingbot.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailMessageHandler implements MessageHandler {
    private final TradeService tradeService;
    private final TelegramBot telegramBot;

    @Override
    @SneakyThrows
    public void handleMessage(Message<?> message) throws MessagingException {
        if (message.getPayload() instanceof MimeMessage) {
            String plainContent = new MimeMessageParser(((MimeMessage) message.getPayload())).parse().getPlainContent();
            log.info("Message: " + plainContent);
            String main = StringUtils.substringBetween(plainContent, "START", "END");
            if (StringUtils.isNoneBlank(main)) {
                telegramBot.sendMsgToOwner(main);
                // TODO: 22.08.2020 refactor this
                if (main.contains("buy")) {
                    try {
                        tradeService.marketBuyAll();
                        telegramBot.sendMsgToOwner("Buy completed " + DateUtils.format(new Date()));
                    } catch (Exception ex) {
                        telegramBot.sendMsgToOwner("Error " + ex.getMessage());
                        log.error("Error ", ex);
                    }
                }
                if (main.contains("sell")) {
                    try {
                        tradeService.marketSellAll();
                        telegramBot.sendMsgToOwner("Sell completed" + DateUtils.format(new Date()));
                    } catch (Exception ex) {
                        telegramBot.sendMsgToOwner("Error " + ex.getMessage());
                        log.error("Error ", ex);
                    }
                }
            }
        }
    }
}
