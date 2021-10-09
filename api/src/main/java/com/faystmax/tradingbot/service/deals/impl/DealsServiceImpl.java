package com.faystmax.tradingbot.service.deals.impl;

import com.faystmax.binance.api.client.domain.ExchangeInfo;
import com.faystmax.binance.api.client.domain.SymbolFilter;
import com.faystmax.binance.api.client.domain.SymbolInfo;
import com.faystmax.binance.api.client.domain.enums.FilterType;
import com.faystmax.binance.api.client.domain.trade.OrderSide;
import com.faystmax.binance.api.client.domain.trade.OrderStatus;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.DealDto;
import com.faystmax.tradingbot.dto.OrderDto;
import com.faystmax.tradingbot.mapper.OrderMapper;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.model.Commission;
import com.faystmax.tradingbot.service.deals.DealsService;
import com.faystmax.tradingbot.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

/**
 * @author Amosov Maxim
 * @since 05.10.2021 : 22:50
 */
@Service
@RequiredArgsConstructor
public class DealsServiceImpl implements DealsService {
    private final static BigDecimal BASE_COMMISSION = new BigDecimal("0.0001");

    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final BinanceService binanceService;

    @Override
    public List<DealDto> getDeals(final User user) {
        final Commission commissions = binanceService.getCommission(user);
        final ExchangeInfo exchangeInfo = binanceService.getExchangeInfo(user);

        return orderService.findOrdersByUser(user).stream()
            .map(orderMapper::map)
            .filter(order -> order.getStatus() == OrderStatus.FILLED)
            .collect(Collectors.groupingBy(OrderDto::getSymbol))
            .values()
            .stream().map(orders -> createDeals(orders, commissions, exchangeInfo))
            .flatMap(Collection::stream)
            .sorted(comparing(DealDto::getIsFilled)
                .thenComparing(DealDto::getLastSellDate, nullsFirst(reverseOrder()))
                .thenComparing(DealDto::getBuyDate, reverseOrder()))
            .peek(deal -> deal.getSellOrders().sort(comparing(OrderDto::getDateUpdate).reversed()))
            .collect(Collectors.toList());
    }


    public List<DealDto> createDeals(
        final List<OrderDto> orders,
        final Commission commissions,
        final ExchangeInfo exchangeInfo
    ) {
        final Map<OrderSide, List<OrderDto>> ordersBySide = orders.stream().collect(Collectors.groupingBy(OrderDto::getSide));

        final List<OrderDto> buyOrders = ordersBySide.getOrDefault(OrderSide.BUY, Collections.emptyList());
        buyOrders.sort(comparing(OrderDto::getDateAdd));

        final List<OrderDto> sellOrders = ordersBySide.getOrDefault(OrderSide.SELL, Collections.emptyList());
        sellOrders.sort(comparing(OrderDto::getDateAdd));
        sellOrders.forEach(orderDto -> orderDto.setNotUsedQty(orderDto.getOrigQty()));

        return buyOrders.stream()
            .map(buyOrder -> {
                final Pair<Boolean, List<OrderDto>> pair = extractSellOrders(buyOrder, sellOrders, commissions, exchangeInfo);
                return new DealDto(buyOrder, pair.getLeft(), pair.getRight());
            })
            .collect(Collectors.toList());
    }

    private Pair<Boolean, List<OrderDto>> extractSellOrders(
        final OrderDto buyOrder,
        final List<OrderDto> sellOrders,
        final Commission commissions,
        final ExchangeInfo exchangeInfo
    ) {
        final ArrayList<OrderDto> extractedSellOrders = new ArrayList<>();
        final Date dateAdd = buyOrder.getDateAdd();
        BigDecimal buyQty = buyOrder.getOrigQty();

        final SymbolInfo symbolInfo = exchangeInfo.getSymbolInfo(buyOrder.getSymbol());
        final SymbolFilter symbolFilter = symbolInfo.getSymbolFilter(FilterType.LOT_SIZE);
        final BigDecimal stepSize = symbolFilter.getStepSize();

        final BigDecimal commission = BASE_COMMISSION.multiply(new BigDecimal(commissions.getMakerCommission()));
        buyQty = buyQty.subtract(buyQty.multiply(commission));
        buyOrder.setOrigQtyWithoutCommission(buyQty);

        while (buyQty.compareTo(stepSize) > 0) {
            final OrderDto sellOrder = getFirstAfter(dateAdd, sellOrders, buyQty);
            if (sellOrder == null) {
                break;
            }

            OrderDto newNotFullyUsedOrder = null;
            final BigDecimal notUsedSellQty = sellOrder.getNotUsedQty();
            final BigDecimal resultQty = buyQty.subtract(notUsedSellQty);
            if (resultQty.compareTo(BigDecimal.ZERO) >= 0) {
                sellOrder.setNotUsedQty(BigDecimal.ZERO);
                buyQty = resultQty;
                newNotFullyUsedOrder = new OrderDto(sellOrder);
            } else {
                final BigDecimal newNotUsedSellQty = notUsedSellQty.subtract(buyQty);
                sellOrder.setNotUsedQty(newNotUsedSellQty);
                newNotFullyUsedOrder = new OrderDto(sellOrder);
                sellOrder.setOrigQty(newNotUsedSellQty);
                buyQty = BigDecimal.ZERO;
            }

            extractedSellOrders.add(newNotFullyUsedOrder);
        }
        return Pair.of(buyQty.compareTo(stepSize) < 0, extractedSellOrders);
    }

    private OrderDto getFirstAfter(final Date dateAdd, final List<OrderDto> sellOrders, final BigDecimal buyQty) {
        for (final OrderDto sellOrder : sellOrders) {
            if (sellOrder.getDateAdd().after(dateAdd)) {
                if (buyQty.subtract(sellOrder.getNotUsedQty()).compareTo(BigDecimal.ZERO) >= 0) {
                    sellOrders.remove(sellOrder);
                }

                return sellOrder;
            }
        }
        return null;
    }
}
