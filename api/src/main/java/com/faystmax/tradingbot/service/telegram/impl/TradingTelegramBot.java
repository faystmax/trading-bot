package com.faystmax.tradingbot.service.telegram.impl;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.config.telegram.TelegramProperties;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.command.CommandExecutor;
import com.faystmax.tradingbot.service.telegram.TelegramBot;
import com.faystmax.tradingbot.service.telegram.TelegramMessageFactory;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Slf4j
@Component
@Profile("master")
public class TradingTelegramBot extends TelegramLongPollingBot implements TelegramBot {
    private static final String START_COMMAND_CODE = "/start";
    private static final String START_AVAILABLE_COMMANDS = "start.available.commands";
    private static final String SEND_MESSAGE = "telegramBot.send.message";
    private static final String SEND_MESSAGE_TO_OWNER = "telegramBot.send.message.to.owner";
    private static final String MESSAGE_FROM_USER = "telegramBot.message.from.user";
    private static final String MESSAGE_FROM_STRANGER = "telegramBot.message.from.stranger";

    private final UserService userService;
    private final MessageSource messageSource;
    private final CommandExecutor commandExecutor;
    private final TelegramProperties telegramProperties;
    private final TelegramMessageFactory messageFactory;
    private final ReplyKeyboardMarkup startKeyboardMarkup;

    @Autowired
    public TradingTelegramBot(UserService userService,
                              DefaultBotOptions options,
                              TelegramProperties telegramProperties,
                              MessageSource messageSource,
                              CommandExecutor commandExecutor,
                              TelegramMessageFactory messageFactory,
                              @Qualifier("startKeyboardMarkup") ReplyKeyboardMarkup startKeyboardMarkup) {
        super(options);
        this.userService = userService;
        this.telegramProperties = telegramProperties;
        this.messageSource = messageSource;
        this.commandExecutor = commandExecutor;
        this.messageFactory = messageFactory;
        this.startKeyboardMarkup = startKeyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }

    @Override
    @SneakyThrows
    public void sendMsg(long chatId, String text) {
        sendMsg(chatId, text, null);
    }

    @SneakyThrows
    private void sendMsg(long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        log.info(messageSource.getMsg(SEND_MESSAGE, text, chatId));
        this.sendApiMethod(messageFactory.createMsg(chatId, text, keyboardMarkup));
    }

    @Override
    @SneakyThrows
    public void sendMsgToOwner(String text) {
        log.info(messageSource.getMsg(SEND_MESSAGE_TO_OWNER, text));
        this.sendApiMethod(messageFactory.createMsg(telegramProperties.getOwnerChatId(), text));
    }

    @Override
    public void onUpdateReceived(Update update) {
        final Message msg = update.getMessage();
        User user = userService.findUserByChatId(msg.getChatId());
        if (user == null) {
            sendMsg(msg.getChatId(), messageSource.getMsg(MESSAGE_FROM_STRANGER, msg.getText(), msg.getChatId()));
            return;
        }

        // Checking start command
        if (START_COMMAND_CODE.equals(msg.getText())) {
            final String replyText = messageSource.getMsg(START_AVAILABLE_COMMANDS);
            this.sendMsg(msg.getChatId(), replyText, startKeyboardMarkup);
            return;
        }

        log.info(messageSource.getMsg(MESSAGE_FROM_USER, msg.getText(), user.getEmail()));
        this.sendMsg(msg.getChatId(), commandExecutor.execute(user, msg.getText()));
    }
}