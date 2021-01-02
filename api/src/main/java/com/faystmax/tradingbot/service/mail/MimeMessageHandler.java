package com.faystmax.tradingbot.service.mail;

import com.faystmax.tradingbot.db.entity.Order;
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
public class MimeMessageHandler implements MessageHandler {
    private final TradeService tradeService;
    private final TelegramBot telegramBot;

    @Override
    @SneakyThrows
    public void handleMessage(Message<?> message) throws MessagingException {
        if (message.getPayload() instanceof MimeMessage) {
            log.info("Received message: " + message.toString());
            MimeMessageParser parse = new MimeMessageParser(((MimeMessage) message.getPayload())).parse();

            String content;
            if (parse.getPlainContent() != null) {
                content = parse.getPlainContent();
                log.info("Message plain: " + content);
            } else {
                content = parse.getHtmlContent();
                log.info("Message html: " + content);
            }

            String mainCommand = StringUtils.substringBetween(content, "START", "END");
            if (StringUtils.isNotBlank(mainCommand)) {
                telegramBot.sendMsgToOwner(mainCommand);
                try {
                    Order order = makeOrder(mainCommand);
                    telegramBot.sendMsgToOwner(order.getSide() + " completed! " +
                        "price = " + order.getPrice().toPlainString() +
                        ", date = " + DateUtils.format(new Date()));
                } catch (Exception ex) {
                    telegramBot.sendMsgToOwner("Error " + ex.getMessage());
                    log.error("Error ", ex);
                }
            }
        }
    }

    private Order makeOrder(String mainCommand) {
        if (mainCommand.contains("buy")) {
            return tradeService.marketBuyAll();
        } else if (mainCommand.contains("sell")) {
            return tradeService.marketSellAll();
        } else {
            throw new RuntimeException("Wrong command! main = " + mainCommand);
        }
    }
}
