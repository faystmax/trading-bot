package com.faystmax.tradingbot.db.repo;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findFirst3ByUserOrderByDateAddDesc(User user);

    @Query("select o from Order o  where o.user = :user order by o.dateAdd desc ")
    List<Order> findAllByUserOrderByDateAddDesc(@Param("user") User user);

    Order findByExchangeId(String exchangeId);
}
