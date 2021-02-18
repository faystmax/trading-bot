package com.faystmax.tradingbot.service.command;

import com.faystmax.tradingbot.db.entity.User;

/**
 * Parsing and executing commands
 */
public interface CommandExecutor {
    /**
     * Parsing and executing command
     *
     * @param user пользователь, у которого запускаем данную команду
     * @param commandText text of the command to parse and execute
     * @return result of the command
     */
    String execute(User user, String commandText);
}
