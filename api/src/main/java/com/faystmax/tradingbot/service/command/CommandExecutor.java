package com.faystmax.tradingbot.service.command;

import com.faystmax.tradingbot.db.entity.User;

/**
 * Parsing and executing commands
 */
public interface CommandExecutor {
    /**
     * Parsing and executing command
     *
     * @param user        user for whom we run this command
     * @param commandText text of the command to parse and execute
     * @return result of the command
     */
    String execute(User user, String commandText);
}
