package com.faystmax.tradingbot.exception;

/**
 * @author Amosov Maxim
 * @since 07.11.2021 : 18:37
 */
public class MailCommandTranslateException extends RuntimeException {
    public MailCommandTranslateException(final String mailText) {
        super("Wrong command! mainText = " + mailText);
    }
}
