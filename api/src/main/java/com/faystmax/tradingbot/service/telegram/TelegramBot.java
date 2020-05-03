package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.config.TelegramConfig;
import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.service.command.CommandExecutor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Objects;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final String START_COMMAND_CODE = "/start";
    private static final String START_AVAILABLE_COMMANDS = "start.available.commands";
    private static final String SEND_MESSAGE = "telegramBot.send.message";
    private static final String SEND_MESSAGE_TO_OWNER = "telegramBot.send.message.to.owner";
    private static final String MESSAGE_FROM_OWNER = "telegramBot.message.from.owner";
    private static final String MESSAGE_FROM_STRANGER = "telegramBot.message.from.stranger";

    private final TelegramConfig config;
    private final MessageSource messageSource;
    private final CommandExecutor commandExecutor;
    private final TelegramMessageFactory messageFactory;
    private final ReplyKeyboardMarkup startKeyboardMarkup;

    @Autowired
    public TelegramBot(DefaultBotOptions options,
                       TelegramConfig config,
                       MessageSource messageSource,
                       CommandExecutor commandExecutor,
                       TelegramMessageFactory messageFactory,
                       @Qualifier("startKeyboardMarkup") ReplyKeyboardMarkup startKeyboardMarkup) {
        super(options);
        this.config = config;
        this.messageSource = messageSource;
        this.commandExecutor = commandExecutor;
        this.messageFactory = messageFactory;
        this.startKeyboardMarkup = startKeyboardMarkup;
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

    public void sendMsgToOwner(final String text) {
        sendMsgToOwner(text, null);
    }

    @SneakyThrows
    public void sendMsgToOwner(final String text, final ReplyKeyboardMarkup keyboardMarkup) {
        log.info(messageSource.getMsg(SEND_MESSAGE_TO_OWNER, text));
        this.sendApiMethod(messageFactory.createMsg(config.getChatId(), text, keyboardMarkup));
    }

    @Override
    public void onUpdateReceived(final Update update) {
        final Message msg = update.getMessage();
        if (!Objects.equals(msg.getChatId(), config.getChatId())) {
            sendMsg(msg.getChatId(), messageSource.getMsg(MESSAGE_FROM_STRANGER, msg.getText(), msg.getChatId()));
            return;
        }

        // Checking start command
        if (START_COMMAND_CODE.equals(msg.getText())) {
            final String replyText = messageSource.getMsg(START_AVAILABLE_COMMANDS);
            this.sendMsgToOwner(replyText, startKeyboardMarkup);
            return;
        }

        log.info(messageSource.getMsg(MESSAGE_FROM_OWNER, msg.getText()));
        this.sendMsgToOwner(commandExecutor.execute(msg.getText()));
    }
}