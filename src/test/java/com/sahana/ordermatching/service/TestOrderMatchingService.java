package com.sahana.ordermatching.service;


import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.enums.OrderType;
import com.sahana.ordermatching.executable.MatchMarketOrder;
import com.sahana.ordermatching.executable.OrderStrategy;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;

@RunWith(MockitoJUnitRunner.class)
public class TestOrderMatchingService {


    @Test
    public void testProcessOrder() {
        OrderStrategy orderStrategy = Mockito.mock(MatchMarketOrder.class);

        Order sellOrder = new OrderBuilder().setOrderId("#1").setPrice(240.1f).
                setQuantity(100).
                setTimeStamp(LocalTime.parse("09:45")).
                setOrderSide(OrderSide.SELL).
                setStockName("BAC").
                setOrderType(OrderType.MARKET_ORDER).
                createOrder();

        Mockito.doNothing().when(orderStrategy).processOrder(sellOrder);

        OrderMatchingService orderMatchingService = new OrderMatchingService();
        orderMatchingService.processOrder(sellOrder);

        //Mockito.verify(orderStrategy).processOrder(sellOrder);

    }
}
