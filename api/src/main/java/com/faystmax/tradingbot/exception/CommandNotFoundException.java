package com.faystmax.tradingbot.exception;

/**
 * Signals an executing of not existing command
 */
public class CommandNotFoundException extends ServiceException {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
