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

import static org.apache.commons.lang3.StringUtils.isBlank;

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
        for (final User user : users) {
            try {
                if (isBlank(user.getBinanceApiKey()) || isBlank(user.getBinanceSecretKey())) {
                    log.info("Binance api or secret key are blank! user = {}", user.getEmail());
                    continue;
                }
                reloadOrdersForUser(user);
            } catch (final Exception ex) {
                log.error("Error while updating orders for user = {}!", user.getEmail(), ex);
            }
        }
        log.info("End reloading orders!");
    }

    public void reloadOrdersForUser(final User user) {
        final String tradingSymbol = user.getTradingSymbol();
        final List<Order> orders = binanceService.getAllMyOrders(user, tradingSymbol);
        for (final Order binanceOrder : orders) {
            final var dbOrder = orderRepo.findByExchangeId(binanceOrder.getOrderId().toString());
            if (Objects.isNull(dbOrder)) {
                orderRepoService.createOrder(binanceOrder);
                log.info("Order for user = {}, created {}", user.getEmail(), binanceOrder.getOrderId());
            } else {
                orderRepoService.updateOrder(user, dbOrder.getId(), binanceOrder);
                log.info("Order for user = {}, updated {}", user.getEmail(), binanceOrder.getOrderId());
            }
        }
    }
}
