package com.faystmax.tradingbot.exception;

/**
 * Signals about Telegram command parse error
 */
public class TelegramCommandParseException extends ServiceException {
    public TelegramCommandParseException(String message) {
        super(message);
    }

    public TelegramCommandParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
