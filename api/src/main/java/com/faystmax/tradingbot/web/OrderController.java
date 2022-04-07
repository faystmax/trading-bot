package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.dto.order.OrderDto;
import com.faystmax.tradingbot.mapper.OrderMapper;
import com.faystmax.tradingbot.service.order.OrderReloader;
import com.faystmax.tradingbot.service.order.OrderService;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final OrderReloader orderReloader;

    @GetMapping
    public Collection<OrderDto> getAllUserOrders(final Principal principal) {
        return orderMapper.mapAll(orderService.findOrdersByUserEmail(principal.getName()));
    }

    @PostMapping("reload")
    public Collection<OrderDto> reloadAndGetOrders(final Principal principal) {
        orderReloader.reloadAllOrdersForUser(userService.findUserByEmail(principal.getName()));
        return orderMapper.mapAll(orderService.findOrdersByUserEmail(principal.getName()));
    }
}
