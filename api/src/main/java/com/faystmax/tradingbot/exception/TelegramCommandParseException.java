package com.faystmax.tradingbot.exception;

/**
 * Signals about Telegram command parse error
 */
public class TelegramCommandParseException extends ServiceException {
    public TelegramCommandParseException(final String message) {
        super(message);
    }

    public TelegramCommandParseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
