package com.faystmax.tradingbot.service.order.impl;

import com.faystmax.binance.api.client.domain.trade.Order;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import com.faystmax.tradingbot.db.repo.UserRepo;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.deals.DealsService;
import com.faystmax.tradingbot.service.deals.SymbolsService;
import com.faystmax.tradingbot.service.order.OrderReloader;
import com.faystmax.tradingbot.service.repo.OrderRepoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReloaderImpl implements OrderReloader {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final DealsService dealsService;
    private final BinanceService binanceService;
    private final SymbolsService symbolsService;
    private final OrderRepoService orderRepoService;

    @Override
//    @Scheduled(initialDelay = 1000, fixedDelay = 3600 * 1000)
    public void reloadOrders() {
        final List<User> users = userRepo.findAll();
        log.info("Start reloading orders! users.size = {}", users.size());
        for (final User user : users) {
            try {
                if (isBlank(user.getBinanceApiKey()) || isBlank(user.getBinanceSecretKey())) {
                    log.info("Binance api or secret key are blank! user = {}", user.getEmail());
                    continue;
                }
                reloadOpenedDeals(user);
            } catch (final Exception ex) {
                log.error("Error while updating orders for user = {}!", user.getEmail(), ex);
            }
        }
        log.info("End reloading orders!");
    }

    public void reloadOpenedDeals(final User user) {
        final Set<String> activeSymbols = dealsService.getDeals(user).stream().filter(dealDto -> !dealDto.getIsFilled())
            .map(dealDto -> dealDto.getBuyOrder().getSymbol())
            .collect(Collectors.toSet());
        reloadOrdersForSymbols(user, activeSymbols);
    }

    @Override
    public void reloadAllOrdersForUser(final User user) {
        final List<String> activeSymbols = symbolsService.updateActiveSymbols(user);
        reloadOrdersForSymbols(user, activeSymbols);
    }

    private void reloadOrdersForSymbols(final User user, final Collection<String> activeSymbols) {
        for (final String activeSymbol : activeSymbols) {
            log.info("Getting all orders for symbol = '{}', user = '{}'", activeSymbol, user.getEmail());
            final List<Order> orders = safeLoadOrders(user, activeSymbol);
            for (final Order binanceOrder : orders) {
                final var dbOrder = orderRepo.findByExchangeId(binanceOrder.getOrderId().toString());
                if (Objects.isNull(dbOrder)) {
                    orderRepoService.createOrder(user, binanceOrder);
                    log.info("Order for user = {}, created {}", user.getEmail(), binanceOrder.getOrderId());
                } else {
                    orderRepoService.updateOrder(user, dbOrder.getId(), binanceOrder);
                    log.info("Order for user = {}, updated {}", user.getEmail(), binanceOrder.getOrderId());
                }
            }
        }
    }

    private List<Order> safeLoadOrders(final User user, final String activeSymbol) {
        try {
            return binanceService.getAllOrdersForSymbol(user, activeSymbol);
        } catch (final Exception ex) {
            log.error("Error during loading orders! user = {}, symbol = {}", user.getEmail(), activeSymbol, ex);
        }
        return Collections.emptyList();
    }
}
