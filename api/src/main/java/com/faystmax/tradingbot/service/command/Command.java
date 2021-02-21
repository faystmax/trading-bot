package com.faystmax.tradingbot.service.command;

import com.faystmax.tradingbot.db.entity.User;

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
     * @param user user for whom we run this command
     * @param args arguments for executing command
     * @return result of the command
     */
    String execute(User user, Collection<String> args);
}
