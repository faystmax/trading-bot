/*
 * Copyright (c)  2020, Amosov Maxim, faystmax@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.component.MessageSource;
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

/**
 * Parsing and executing telegram commands
 */
@Slf4j
@Component
public class TelegramCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NOT_FOUND = "executor.command.not.found";
    public static final String COMMAND_PROCESSING_ERROR = "executor.command.processing.error";

    private final MessageSource messageSource;
    private final TelegramCommandParser telegramCommandParser;
    private final Map<String, Command> commandsMap;

    @Autowired
    public TelegramCommandExecutor(MessageSource messageSource,
                                   TelegramCommandParser telegramCommandParser,
                                   List<Command> commands) {
        this.messageSource = messageSource;
        this.telegramCommandParser = telegramCommandParser;
        this.commandsMap = commands.stream().collect(toUnmodifiableMap(Command::getCode, identity()));
    }


    @Override
    public String execute(final String commandText) {
        try {
            final Pair<String, Collection<String>> codeAndArgs = telegramCommandParser.parse(commandText);
            if (!commandsMap.containsKey(codeAndArgs.getKey())) {
                throw new CommandNotFoundException(messageSource.getMsg(COMMAND_NOT_FOUND, codeAndArgs.getKey()));
            }

            // Executing command
            return commandsMap.get(codeAndArgs.getKey()).execute(codeAndArgs.getValue());
        } catch (Exception ex) {
            String msg = messageSource.getMsg(COMMAND_PROCESSING_ERROR, commandText);
            log.error(msg, ex);
            return msg;
        }
    }
}
