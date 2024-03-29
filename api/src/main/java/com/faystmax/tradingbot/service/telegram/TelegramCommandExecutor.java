package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.exception.CommandNotFoundException;
import com.faystmax.tradingbot.service.command.Command;
import com.faystmax.tradingbot.service.command.CommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Slf4j
@Component
public class TelegramCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NOT_FOUND = "executor.command.not.found";
    public static final String COMMAND_PROCESSING_ERROR = "executor.command.processing.error";

    private final MessageSource messageSource;
    private final TelegramCommandParser telegramCommandParser;
    private final Map<String, Command> commandsMap;

    @Autowired
    public TelegramCommandExecutor(
        final MessageSource messageSource,
        final TelegramCommandParser telegramCommandParser,
        final List<Command> commands
    ) {
        this.messageSource = messageSource;
        this.telegramCommandParser = telegramCommandParser;
        this.commandsMap = commands.stream().collect(toUnmodifiableMap(Command::getCode, identity()));
    }

    @Override
    public String execute(final User user, final String commandText) {
        try {
            final Pair<String, Collection<String>> codeAndArgs = telegramCommandParser.parse(commandText);
            if (!commandsMap.containsKey(codeAndArgs.getKey())) {
                throw new CommandNotFoundException(messageSource.getMsg(COMMAND_NOT_FOUND, codeAndArgs.getKey()));
            }

            // Executing command
            final Command command = commandsMap.get(codeAndArgs.getKey());
            return command.execute(user, codeAndArgs.getValue());
        } catch (final Exception ex) {
            final String msg = messageSource.getMsg(COMMAND_PROCESSING_ERROR, commandText, ex.getMessage());
            log.error(msg, ex);
            return msg;
        }
    }
}
