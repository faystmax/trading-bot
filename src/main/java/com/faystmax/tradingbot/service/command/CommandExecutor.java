package com.faystmax.tradingbot.service.command;

/**
 * Parsing and executing commands
 */
public interface CommandExecutor {
    /**
     * Parsing and executing command
     *
     * @param commandText - text of the command to parse and execute
     * @return result of the command
     */
    String execute(String commandText);
}
