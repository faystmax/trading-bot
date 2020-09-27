package com.faystmax.tradingbot.db.repo;

import com.faystmax.tradingbot.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findFirst3ByOrderByDateAddAsc();
}
