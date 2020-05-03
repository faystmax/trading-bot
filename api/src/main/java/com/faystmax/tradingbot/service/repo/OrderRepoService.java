package com.faystmax.tradingbot.service.repo;

import com.faystmax.tradingbot.db.entity.Order;
import com.faystmax.tradingbot.db.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderRepoService {
    private final OrderRepo repo;

    @Transactional
    public void deleteAll() {
        repo.deleteAll();
    }

    @Transactional
    public Order createOrder(com.binance.api.client.domain.account.Order binanceOrder) {
        var myOrder = new Order();
        myOrder.setExchangeId(binanceOrder.getOrderId().toString());
        myOrder.setDateAdd(new Date(binanceOrder.getTime()));
        myOrder.setPrice(new BigDecimal(binanceOrder.getPrice()));
        myOrder.setStopPrice(new BigDecimal(binanceOrder.getStopPrice()));
        myOrder.setOrigQty(new BigDecimal(binanceOrder.getOrigQty()));
        myOrder.setExecutedQty(new BigDecimal(binanceOrder.getExecutedQty()));
        myOrder.setIcebergQty(new BigDecimal(binanceOrder.getIcebergQty()));
        myOrder.setStatus(binanceOrder.getStatus());
        myOrder.setTimeInForce(binanceOrder.getTimeInForce());
        myOrder.setType(binanceOrder.getType());
        myOrder.setSide(binanceOrder.getSide());
        repo.save(myOrder);
        return myOrder;
    }
}
