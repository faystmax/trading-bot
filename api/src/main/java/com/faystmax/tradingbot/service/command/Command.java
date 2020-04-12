package com.faystmax.tradingbot.service.command;

import java.util.Collection;

/**
 * Common interface for all command Executors
 */
public interface Command {
    /**
     * @return code of the command
     */
    String getCode();

    /**
     * @return description of the command
     */
    String getDescription();

    /**
     * Executing main command logic
     *
     * @param args - args for executing command
     * @return result of the command
     */
    String execute(Collection<String> args);
}
