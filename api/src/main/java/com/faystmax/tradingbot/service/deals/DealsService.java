package com.faystmax.tradingbot.service.deals;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.DealDto;

import java.util.List;

public interface DealsService {
    /**
     * @param user user
     * @return user deals
     */
    List<DealDto> getDeals(User user);
}
