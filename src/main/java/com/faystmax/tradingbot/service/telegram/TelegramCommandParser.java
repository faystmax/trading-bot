package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.component.MessageSource;
import com.faystmax.tradingbot.exception.TelegramCommandParseException;
import com.faystmax.tradingbot.service.command.CommandParser;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TelegramCommandParser implements CommandParser {
    public static final String PARSER_PARSE_ERROR = "parser.parse.error";

    private final MessageSource messageSource;

    public Pair<String, Collection<String>> parse(String command) {
        try {
            command = command.trim();
            if (command.isBlank() || !command.startsWith("/")) {
                throw new TelegramCommandParseException(messageSource.getMsg(PARSER_PARSE_ERROR, command));
            }

            command = command.substring(1);
            List<String> parts = Lists.newArrayList(command.split("\\s+"));
            String commandCode = parts.remove(0);
            return Pair.of(commandCode, parts);
        } catch (TelegramCommandParseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TelegramCommandParseException(messageSource.getMsg(PARSER_PARSE_ERROR, command), ex);
        }
    }
}
