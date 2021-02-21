package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramMessageService implements MessageService {
    private final TelegramBot telegramBot;

    @Override
    public void sendMessageToOwner(String message) {
        telegramBot.sendMsgToOwner(message);
    }

    @Override
    public void sendMessageToUser(User user, String message) {
        telegramBot.sendMsg(user.getTelegramChatId(), message);
    }
}
