package com.sahana.ordermatching.order;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.enums.OrderType;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class OrderBuilder {
    private String orderId;
    private OrderSide orderSide;
    private float price;
    private String stockName;
    private int quantity;
    private LocalTime timeStamp;
    private OrderType orderType;
    private float limit;

    public OrderBuilder setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
        return this;
    }

    public OrderBuilder setPrice(float price) {
        this.price = price;
        return this;
    }

    public OrderBuilder setStockName(String stockName) {
        this.stockName = stockName;
        return this;
    }

    public OrderBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public OrderBuilder setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public OrderBuilder setLimit(float limit) {
        this.limit = limit;
        return this;
    }

    public Order createOrder() {
        return new Order(orderId, orderSide, price, stockName, quantity, timeStamp, orderType, limit);
    }
}