package com.faystmax.tradingbot.service.deals;

import com.faystmax.tradingbot.db.entity.User;

import java.util.List;

public interface SymbolsService {
    /**
     * Update active symbols for user
     *
     * @param user user
     * @return new active symbols
     */
    List<String> updateActiveSymbols(User user);
}
