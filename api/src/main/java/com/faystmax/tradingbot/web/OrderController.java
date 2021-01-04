package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.dto.OrderDto;
import com.faystmax.tradingbot.mapper.OrderMapper;
import com.faystmax.tradingbot.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/orders")
    public Collection<OrderDto> getAllOrders() {
        return orderMapper.mapAll(orderService.findAllOrders());
    }
}
