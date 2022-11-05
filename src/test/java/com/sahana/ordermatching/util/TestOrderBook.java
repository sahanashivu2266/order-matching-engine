package com.sahana.ordermatching.util;


import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrderBook {


    @BeforeAll
    static void clearOrderBook() {
        OrderBook.getOrderBook().getBuyOrderBook().clear();
        OrderBook.getOrderBook().getSellOrderBook().clear();
    }


    @Test
    @org.junit.jupiter.api.Order(1)
    void testSingleTonOrderBook() {
        OrderBook orderBook1 = OrderBook.getOrderBook();
        OrderBook orderBook2 = OrderBook.getOrderBook();

        Assert.assertEquals(orderBook1, orderBook2);
        Assert.assertNotNull(orderBook1.getSellOrderBook());
        Assert.assertNotNull(orderBook1.getBuyOrderBook());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testAddOrderToOrderBook_Sell_Order() {
        Order sellOrder = new OrderBuilder().setOrderId("#1").setPrice(240.1f).setQuantity(100).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();

        Assert.assertNull(OrderBook.getOrderBook().getSellOrderBook().get("BAC"));

        OrderBook.getOrderBook().addOrderToOrderBook(sellOrder);

        Assert.assertEquals(true, OrderBook.getOrderBook().getSellOrderBook().containsKey("BAC"));
        Assert.assertEquals(1, OrderBook.getOrderBook().getSellOrderBook().get("BAC").size());
        Assert.assertEquals(false, OrderBook.getOrderBook().getSellOrderBook().containsKey("HDFC"));
    }


    @Test
    @org.junit.jupiter.api.Order(3)
    void testAddOrderToOrderBook_Buy_Order() {
        Order buyOrder = new OrderBuilder().setOrderId("#1").setPrice(240.1f).setQuantity(100).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();

        Assert.assertNull(OrderBook.getOrderBook().getBuyOrderBook().get("BAC"));

        OrderBook.getOrderBook().addOrderToOrderBook(buyOrder);

        Assert.assertEquals(true, OrderBook.getOrderBook().getBuyOrderBook().containsKey("BAC"));
        Assert.assertEquals(1, OrderBook.getOrderBook().getBuyOrderBook().get("BAC").size());
        Assert.assertEquals(false, OrderBook.getOrderBook().getBuyOrderBook().containsKey("HDFC"));
    }


    @Test
    @org.junit.jupiter.api.Order(4)
    void testAddOrderToOrderBook_Sell_Order_2() {
        Order sellOrder = new OrderBuilder().setOrderId("#2").setPrice(240.1f).setQuantity(100).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();

        OrderBook.getOrderBook().addOrderToOrderBook(sellOrder);

        Assert.assertEquals(true, OrderBook.getOrderBook().getSellOrderBook().containsKey("BAC"));
        Assert.assertEquals(2, OrderBook.getOrderBook().getSellOrderBook().get("BAC").size());
        Assert.assertEquals(false, OrderBook.getOrderBook().getSellOrderBook().containsKey("HDFC"));
    }


    @Test
    @org.junit.jupiter.api.Order(5)
    void testAddOrderToOrderBook_Buy_Order_2() {
        Order buyOrder = new OrderBuilder().setOrderId("#2").setPrice(240.1f).setQuantity(100).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();

        OrderBook.getOrderBook().addOrderToOrderBook(buyOrder);

        Assert.assertEquals(true, OrderBook.getOrderBook().getBuyOrderBook().containsKey("BAC"));
        Assert.assertEquals(2, OrderBook.getOrderBook().getBuyOrderBook().get("BAC").size());
        Assert.assertEquals(false, OrderBook.getOrderBook().getBuyOrderBook().containsKey("HDFC"));
    }
}
