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
