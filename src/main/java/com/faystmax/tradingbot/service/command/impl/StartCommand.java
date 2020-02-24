package com.faystmax.tradingbot.service.command.impl;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.service.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * Displays all available bot commands
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartCommand implements Command {
    private static final String START_CODE = "start";
    private static final String START_DESCRIPTION = "start.description";
    private static final String START_AVAILABLE_COMMANDS = "start.available.commands";

    private final MessageSource messageSource;
    private Map<String, Command> commandsMap;

    @Autowired
    public void init(List<Command> commands) {
        commandsMap = commands.stream().collect(toUnmodifiableMap(Command::getCode, identity()));
    }

    @Override
    public String getCode() {
        return START_CODE;
    }

    @Override
    public String getDescription() {
        return messageSource.getMsg(START_DESCRIPTION);
    }

    @Override
    public String execute(Collection<String> args) {
        var builder = new StringBuilder();
        builder.append(messageSource.getMsg(START_AVAILABLE_COMMANDS)).append(":\n\n");
        commandsMap.forEach((key, value) -> {
            builder.append("/");
            builder.append(key);
            builder.append(" \u2014 ");
            builder.append(value.getDescription());
            builder.append("\n");
        });
        return builder.toString();
    }
}
