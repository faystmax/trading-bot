package com.faystmax.tradingbot.config.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Wrapper above default spring messageSource
 */
@Component("messageSourceWrapper")
@RequiredArgsConstructor
public class MessageSource {
    private final org.springframework.context.MessageSource messageSource;

    /**
     * Return filled message with the specified code
     *
     * @param code code of the message
     * @param args an array of arguments that will be filled in message
     * @return filled message
     */
    public String getMsg(final String code, final Object... args) {
        return this.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
