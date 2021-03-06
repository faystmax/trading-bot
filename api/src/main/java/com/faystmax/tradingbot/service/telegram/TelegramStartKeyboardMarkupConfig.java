package com.faystmax.tradingbot.service.telegram;

import com.faystmax.tradingbot.service.command.Command;
import org.apache.commons.collections4.ListUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;

@Configuration
public class TelegramStartKeyboardMarkupConfig {
    @Bean("startKeyboardMarkup")
    public ReplyKeyboardMarkup keyboardMarkup(final List<Command> commands) {
        var keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<List<Command>> parts = ListUtils.partition(commands, 2);
        keyboardMarkup.setKeyboard(parts.stream().map(this::createRow).collect(toList()));
        return keyboardMarkup;
    }

    private KeyboardRow createRow(final List<Command> commands) {
        return commands.stream()
            .map(Command::getCode)
            .map(KeyboardButton::new)
            .collect(Collector.of(KeyboardRow::new, KeyboardRow::add, (left, right) -> {
                left.addAll(right);
                return left;
            }));
    }
}
