package com.faystmax.tradingbot.db.repo;

import com.faystmax.tradingbot.db.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepo extends JpaRepository<Trade, Long> {
}
