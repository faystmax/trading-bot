package com.faystmax.tradingbot.exception;

import lombok.NoArgsConstructor;

/**
 * Base exception
 */
@NoArgsConstructor
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
