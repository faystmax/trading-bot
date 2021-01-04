package com.faystmax.tradingbot.service.order.impl;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public Collection<Order> findAllOrders() {
        return orderRepo.findAllByOrderByDateAddDesc();
    }
}
