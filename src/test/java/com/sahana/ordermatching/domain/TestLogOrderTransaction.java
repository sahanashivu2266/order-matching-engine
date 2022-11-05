package com.sahana.ordermatching.domain;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class TestLogOrderTransaction {


    @Test
    public void testOrderTransactionLog() {
        Order sellOrder = new OrderBuilder().setOrderId("#1").setPrice(140.1f).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        Order buyOrder = new OrderBuilder().setOrderId("#2").setPrice(141.1f).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();

        OrderTransaction orderTransaction = OrderTransaction.logOrderTransaction(sellOrder, buyOrder, 80);
        Assert.assertEquals("#1 80 140.1 #2", orderTransaction.toString());
    }
}
