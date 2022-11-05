package com.sahana.ordermatching.domain;

import com.sahana.ordermatching.order.Order;

import java.util.Comparator;

public class SellOrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        int i = Float.compare(o1.getPrice(), o2.getPrice());
        if (i != 0) {
            return i;
        }
        i = o1.getTimeStamp().compareTo(o2.getTimeStamp());
        return i;
    }
}
