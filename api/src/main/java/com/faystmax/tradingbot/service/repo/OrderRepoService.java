package com.faystmax.tradingbot.service.repo;

import com.faystmax.binance.api.client.domain.trade.NewOrderResponse;
import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderRepoService {
    private final OrderRepo repo;

    @Transactional
    public Order createOrder(com.faystmax.binance.api.client.domain.trade.Order binanceOrder) {
        var myOrder = new Order();
        myOrder.setExchangeId(binanceOrder.getOrderId().toString());
        myOrder.setDateAdd(new Date(binanceOrder.getTime()));
        myOrder.setPrice(binanceOrder.getPrice());
        myOrder.setStopPrice(binanceOrder.getStopPrice());
        myOrder.setOrigQty(binanceOrder.getOrigQty());
        myOrder.setExecutedQty(binanceOrder.getExecutedQty());
        myOrder.setIcebergQty(binanceOrder.getIcebergQty());
        myOrder.setStatus(binanceOrder.getStatus());
        myOrder.setTimeInForce(binanceOrder.getTimeInForce());
        myOrder.setType(binanceOrder.getType());
        myOrder.setSide(binanceOrder.getSide());
        repo.save(myOrder);
        return myOrder;
    }

    @Transactional
    public Order createOrder(User user, NewOrderResponse orderResponse) {
        var myOrder = new Order();
        myOrder.setExchangeId(orderResponse.getOrderId().toString());
        myOrder.setDateAdd(new Date());
        myOrder.setPrice(orderResponse.getPrice());
        myOrder.setOrigQty(orderResponse.getOrigQty());
        myOrder.setExecutedQty(orderResponse.getExecutedQty());
        myOrder.setStatus(orderResponse.getStatus());
        myOrder.setTimeInForce(orderResponse.getTimeInForce());
        myOrder.setType(orderResponse.getType());
        myOrder.setSide(orderResponse.getSide());
        myOrder.setUser(user);

        // if order has zero price, then calculate average price by orders fills
        if (myOrder.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            setAveragePriceByFills(orderResponse, myOrder);
        }
        repo.save(myOrder);
        return myOrder;
    }

    private void setAveragePriceByFills(NewOrderResponse response, Order order) {
        response.getFills().stream().map(trade -> {
            BigDecimal part = trade.getQty().divide(response.getExecutedQty(), RoundingMode.HALF_DOWN);
            return part.multiply(trade.getPrice());
        }).reduce(BigDecimal::add).ifPresent(order::setPrice);
    }
}
