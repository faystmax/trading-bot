package com.faystmax.tradingbot.exception;

import lombok.NoArgsConstructor;

/**
 * Base exception
 */
@NoArgsConstructor
public class ServiceException extends RuntimeException {
    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
