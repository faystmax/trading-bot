package com.faystmax.tradingbot.service.deals.impl;

import com.faystmax.binance.api.client.domain.ExchangeInfo;
import com.faystmax.binance.api.client.domain.SymbolInfo;
import com.faystmax.binance.api.client.domain.enums.FilterType;
import com.faystmax.binance.api.client.domain.trade.OrderSide;
import com.faystmax.binance.api.client.domain.trade.OrderStatus;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.dto.OrderDto;
import com.faystmax.tradingbot.dto.order.BuyOrderDto;
import com.faystmax.tradingbot.dto.order.DealDto;
import com.faystmax.tradingbot.dto.order.SellOrderDto;
import com.faystmax.tradingbot.mapper.OrderMapper;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.binance.model.Commission;
import com.faystmax.tradingbot.service.deals.DealsService;
import com.faystmax.tradingbot.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.faystmax.binance.api.client.domain.trade.OrderSide.BUY;
import static com.faystmax.binance.api.client.domain.trade.OrderSide.SELL;
import static java.util.Collections.emptyList;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Amosov Maxim
 * @since 05.10.2021 : 22:50
 */
@Service
@RequiredArgsConstructor
public class DealsServiceImpl implements DealsService {
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
            .collect(groupingBy(OrderDto::getSymbol))
            .values()
            .stream()
            .flatMap(orders -> createDeals(orders, commissions, exchangeInfo).stream())
            .sorted(comparing(DealDto::getIsFilled)
                .thenComparing(DealDto::getLastSellDate, nullsFirst(reverseOrder()))
                .thenComparing(DealDto::getBuyDate, reverseOrder())
            ).peek(deal -> deal.getSellOrders().sort(comparing(OrderDto::getDateUpdate).reversed()))
            .collect(Collectors.toList());
    }

    public List<DealDto> createDeals(
        final List<OrderDto> orders,
        final Commission commission,
        final ExchangeInfo exchangeInfo
    ) {
        final Map<OrderSide, List<OrderDto>> ordersBySide = orders.stream().collect(groupingBy(OrderDto::getSide));

        final List<SellOrderDto> sellOrders = ordersBySide.getOrDefault(SELL, emptyList())
            .stream()
            .map(SellOrderDto::new)
            .sorted(comparing(SellOrderDto::getDateAdd))
            .collect(Collectors.toList());

        return ordersBySide.getOrDefault(BUY, emptyList())
            .stream()
            .map(orderDto -> new BuyOrderDto(orderDto, commission))
            .sorted(comparing(BuyOrderDto::getDateAdd))
            .map(buyOrder -> new DealDto(buyOrder, extractSellOrders(buyOrder, sellOrders, exchangeInfo)))
            .collect(Collectors.toList());
    }


    private List<SellOrderDto> extractSellOrders(
        final BuyOrderDto buyOrder,
        final List<SellOrderDto> sellOrders,
        final ExchangeInfo exchangeInfo
    ) {
        final ArrayList<SellOrderDto> extractedSellOrders = new ArrayList<>();
        BigDecimal buyQty = buyOrder.getQtyWithoutCommission();
        final BigDecimal stepSize = getSymbolStepSize(buyOrder.getSymbol(), exchangeInfo);
        while (buyQty.compareTo(stepSize) >= 0) {
            final SellOrderDto sellOrder = getFirstAfter(buyOrder.getDateAdd(), sellOrders);
            if (sellOrder == null) {
                break;
            }

            SellOrderDto sellOrderCopy;
            final BigDecimal notUsedSellQty = sellOrder.getNotUsedQty();
            final BigDecimal resultQty = buyQty.subtract(notUsedSellQty);
            if (resultQty.compareTo(BigDecimal.ZERO) >= 0) {
                sellOrders.remove(sellOrder);
                sellOrder.setNotUsedQty(BigDecimal.ZERO);
                buyQty = resultQty;
                sellOrderCopy = new SellOrderDto(sellOrder,BigDecimal.ZERO);
            } else {
                final BigDecimal newNotUsedSellQty = notUsedSellQty.subtract(buyQty);
                sellOrder.setNotUsedQty(newNotUsedSellQty);
                sellOrderCopy = new SellOrderDto(sellOrder, newNotUsedSellQty);
                sellOrder.setOrigQty(newNotUsedSellQty);
                buyQty = BigDecimal.ZERO;
            }

            extractedSellOrders.add(sellOrderCopy);
        }

        buyOrder.setNotSoldQty(buyQty);
        buyOrder.setIsFullySold(buyQty.compareTo(stepSize) < 0);
        return extractedSellOrders;
    }

    private BigDecimal getSymbolStepSize(final String symbol, final ExchangeInfo exchangeInfo) {
        final SymbolInfo symbolInfo = exchangeInfo.getSymbolInfo(symbol);
        return symbolInfo.getSymbolFilter(FilterType.LOT_SIZE).getStepSize();
    }

    private SellOrderDto getFirstAfter(final Date dateStart, final List<SellOrderDto> sellOrders) {
        return sellOrders.stream().filter(order -> order.getDateAdd().after(dateStart)).findFirst().orElse(null);
    }
}
