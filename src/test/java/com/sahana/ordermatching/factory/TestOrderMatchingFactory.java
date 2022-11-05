package com.sahana.ordermatching.factory;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.enums.OrderType;
import com.sahana.ordermatching.executable.OrderStrategy;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOrderMatchingFactory {

    @Test
    public void testOrderMatchingFactory_MARKET_ORDER() {
        Order sellOrder = new OrderBuilder().setOrderId("#1").setPrice(240.1f).
                setQuantity(100).
                setTimeStamp(LocalTime.parse("09:45")).
                setOrderSide(OrderSide.SELL).
                setStockName("BAC").
                setOrderType(OrderType.MARKET_ORDER).
                createOrder();
        OrderStrategy strategy = OrderMatchingFactory.getExecutableThroughOrderType(sellOrder);

        Assert.assertNotNull(strategy);
    }


    @Test
    public void testOrderMatchingFactory_LIMIT_ORDER() {
        Order sellOrder = new OrderBuilder().setOrderId("#1").setPrice(240.1f).setQuantity(100).
                setTimeStamp(LocalTime.parse("09:45")).
                setOrderSide(OrderSide.SELL).
                setStockName("BAC").
                setOrderType(OrderType.LIMIT_ORDER).
                createOrder();

        Exception exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            OrderMatchingFactory.getExecutableThroughOrderType(sellOrder);
        });

        String expectedMessage = "Limit order is not supported yet";
        String actualMessage = exception.getMessage();
        Assert.assertNotNull(exception);
        assertTrue(actualMessage.contains(expectedMessage));

    }

}
