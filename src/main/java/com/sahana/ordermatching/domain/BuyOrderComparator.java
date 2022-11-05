package com.sahana.ordermatching.domain;

import com.sahana.ordermatching.order.Order;

import java.util.Comparator;

public class BuyOrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPrice() == o2.getPrice()) {
            return o1.getTimeStamp().compareTo(o2.getTimeStamp());
        } else {
            return o1.getPrice() > o2.getPrice() ? -1 : 1;
        }


    }
}
