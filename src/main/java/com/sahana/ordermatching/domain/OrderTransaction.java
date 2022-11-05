package com.sahana.ordermatching.domain;

import com.sahana.ordermatching.order.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class OrderTransaction {


    private String sellOrderId;

    private int quantity;

    private float sellPrice;

    private String buyOrderId;

    public static OrderTransaction logOrderTransaction(Order sellOrder, Order buyOrder, int soldQuantity) {
        OrderTransaction orderPlaced = new OrderTransaction(sellOrder.getOrderId(), soldQuantity, sellOrder.getPrice(), buyOrder.getOrderId());
        return orderPlaced;
    }

    @Override
    public String toString() {
        return sellOrderId + " " + quantity + " " + sellPrice + " " + buyOrderId;
    }
}
