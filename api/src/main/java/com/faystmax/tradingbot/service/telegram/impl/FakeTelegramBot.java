package com.faystmax.tradingbot.service.telegram.impl;

import com.faystmax.tradingbot.service.telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!master")
public class FakeTelegramBot implements TelegramBot {
    @Override
    public void sendMsg(long chatId, String text) {
        log.info("Fake send message to chatId = ''{}'', text = ''{}''", chatId, text);
    }

    @Override
    public void sendMsgToOwner(String text) {
        log.info("Fake send message to owner, text = ''{}''", text);
    }
}
