package com.faystmax.tradingbot.service.command;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;

/**
 * Basic interface for parsing commands
 */
public interface CommandParser {
    /**
     * Parse a command
     *
     * @return pair of code and its args
     */
    Pair<String, Collection<String>> parse(String command);
}
