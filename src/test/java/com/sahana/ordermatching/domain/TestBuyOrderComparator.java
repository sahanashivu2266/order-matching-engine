package com.sahana.ordermatching.domain;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class TestBuyOrderComparator {

    @Test
    public void testBuyOrderCompare_dif_price() {
        Order o1 = new OrderBuilder().setOrderId("#1").setPrice(140.1f).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        Order o2 = new OrderBuilder().setOrderId("#2").setPrice(141.1f).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();

        BuyOrderComparator buyOrderComparator = new BuyOrderComparator();
        int dif = buyOrderComparator.compare(o1, o2);
        assertEquals(1, dif);
    }


    @Test
    public void testBuyOrderCompare_same_price_same_timestamp() {
        Order o1 = new OrderBuilder().setOrderId("#1").setPrice(140.1f).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        Order o2 = new OrderBuilder().setOrderId("#2").setPrice(140.1f).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();

        BuyOrderComparator buyOrderComparator = new BuyOrderComparator();
        int dif = buyOrderComparator.compare(o1, o2);
        assertEquals(0, dif);
    }

    @Test
    public void testBuyOrderCompare_same_price_dif_timestamp() {
        Order o1 = new OrderBuilder().setOrderId("#1").setPrice(140.1f).setTimeStamp(LocalTime.parse("09:40")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        Order o2 = new OrderBuilder().setOrderId("#2").setPrice(140.1f).setTimeStamp(LocalTime.parse("09:35")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();

        BuyOrderComparator buyOrderComparator = new BuyOrderComparator();
        int dif = buyOrderComparator.compare(o1, o2);
        assertEquals(1, dif);
    }

}

