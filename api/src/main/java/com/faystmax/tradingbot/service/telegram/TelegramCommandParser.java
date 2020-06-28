package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.config.message.MessageSource;
import com.faystmax.tradingbot.exception.TelegramCommandParseException;
import com.faystmax.tradingbot.service.command.CommandParser;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramCommandParser implements CommandParser {
    public static final String PARSE_ERROR = "parser.parse.error";

    private final MessageSource messageSource;

    public Pair<String, Collection<String>> parse(final String command) {
        try {
            if (command.isBlank()) {
                throw new TelegramCommandParseException(messageSource.getMsg(PARSE_ERROR, command));
            }

            List<String> parts = Lists.newArrayList(command.split("\\s+"));
            String commandCode = parts.remove(0);
            return Pair.of(commandCode, parts);
        } catch (TelegramCommandParseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TelegramCommandParseException(messageSource.getMsg(PARSE_ERROR, command), ex);
        }
    }
}
