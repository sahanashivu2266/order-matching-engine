package com.sahana.ordermatching.service;

import com.sahana.ordermatching.executable.OrderStrategy;
import com.sahana.ordermatching.factory.OrderMatchingFactory;
import com.sahana.ordermatching.order.Order;

public class OrderMatchingService {

    public void processOrder(Order order) {
        OrderStrategy orderStrategy = OrderMatchingFactory.getExecutableThroughOrderType(order);
        orderStrategy.processOrder(order);

    }
}
