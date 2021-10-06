package com.faystmax.tradingbot.service.order.impl;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.order.OrderService;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final UserService userService;

    @Override
    public List<Order> findAllOrders() {
        return orderRepo.findAllByOrderByDateAddDesc();
    }

    @Override
    public List<Order> findOrdersByUserEmail(final String userEmail) {
        return findOrdersByUser(userService.findUserByEmail(userEmail));
    }

    @Override
    public List<Order> findOrdersByUser(final User user) {
        return orderRepo.findAllByUserOrderByDateAddDesc(user);
    }
}
