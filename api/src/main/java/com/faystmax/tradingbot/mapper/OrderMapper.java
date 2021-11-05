package com.faystmax.tradingbot.mapper;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto map(final Order order);

    Collection<OrderDto> mapAll(final Collection<Order> orders);
}
