package com.faystmax.tradingbot.service.order.impl;

import com.faystmax.binance.api.client.domain.trade.Order;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.order.OrderReloader;
import com.faystmax.tradingbot.service.repo.OrderRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Amosov Maxim
 * @since 30.09.2021 : 23:07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReloaderImpl implements OrderReloader {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final BinanceService binanceService;
    private final OrderRepoService orderRepoService;

    @Override
    @Scheduled(initialDelay = 1000, fixedDelay = 3600 * 1000)
    public void reloadOrders() {
        final List<User> users = userRepo.findAll();
        log.info("Start reloading orders! users.size = {}", users.size());
        for (User user : users) {
            try {
                final String tradingSymbol = user.getTradingSymbol();
                final List<Order> orders = binanceService.getAllMyOrders(user, tradingSymbol);
                for (final Order order : orders) {
                    final var dbOrder = orderRepo.findByExchangeId(order.getOrderId().toString());
                    if (Objects.isNull(dbOrder)) {
                        final com.faystmax.tradingbot.db.entity.Order newOrder = orderRepoService.createOrder(order);
                        log.info("Order for user = {}, created {}", user.getEmail(), newOrder.getId());
                    } else {
                        orderRepoService.updateOrder(user, dbOrder.getId(), order);
                        log.info("Order for user = {}, updated {}", user.getEmail(), dbOrder.getId());
                    }
                }
            } catch (final Exception ex) {
                log.error("Error while updating orders for user = {}!", user.getEmail(), ex);
            }
        }
        log.info("End reloading orders!}");
    }
}
