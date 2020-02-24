package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.config.TelegramConfig;
import com.faystmax.tradingbot.service.command.CommandExecutor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    public static final String SEND_MESSAGE = "telegramBot.send.message";
    public static final String SEND_MESSAGE_TO_OWNER = "telegramBot.send.message.to.owner";
    public static final String MESSAGE_FROM_OWNER = "telegramBot.message.from.owner";
    public static final String MESSAGE_FROM_STRANGER = "telegramBot.message.from.stranger";

    private final TelegramConfig config;
    private final MessageSource messageSource;
    private final CommandExecutor commandExecutor;
    private final TelegramMessageFactory messageFactory;

    @Autowired
    public TelegramBot(DefaultBotOptions options,
                       TelegramConfig config,
                       MessageSource messageSource,
                       CommandExecutor commandExecutor,
                       TelegramMessageFactory messageFactory) {
        super(options);
        this.config = config;
        this.messageSource = messageSource;
        this.commandExecutor = commandExecutor;
        this.messageFactory = messageFactory;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    public void sendMsg(final long chatId, final String text) {
        log.info(messageSource.getMsg(SEND_MESSAGE, text, chatId));
        this.sendApiMethod(messageFactory.createMsg(chatId, text));
    }

    @SneakyThrows
    public void sendMsgToOwner(final String text) {
        log.info(messageSource.getMsg(SEND_MESSAGE_TO_OWNER, text));
        this.sendApiMethod(messageFactory.createMsg(config.getChatId(), text));
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(final Update update) {
        final Message msg = update.getMessage();
        if (!Objects.equals(msg.getChatId(), config.getChatId())) {
            final String message = messageSource.getMsg(MESSAGE_FROM_STRANGER, msg.getText(), msg.getChatId());
            sendMsg(msg.getChatId(), message);
            return;
        }

        // Received message from owner
        log.info(messageSource.getMsg(MESSAGE_FROM_OWNER, msg.getText()));

        // Execute command and send command to Owner
        sendMsgToOwner(commandExecutor.execute(msg.getText()));
    }
}