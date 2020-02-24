package com.faystmax.tradingbot.service.notification.impl;

import com.faystmax.tradingbot.service.notification.NotificationService;
import com.faystmax.tradingbot.service.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationServiceImpl implements NotificationService {
    private final TelegramBot telegramBot;

    @Override
    public boolean sendMessage(String title, String text) {
        telegramBot.sendMsgToOwner(text);
        return true;
    }
}
