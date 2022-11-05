package com.sahana.ordermatching.executable;

import com.sahana.ordermatching.order.Order;

public interface OrderStrategy {

    void processOrder(Order order);
}
