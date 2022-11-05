package com.sahana.ordermatching.util;

import com.sahana.ordermatching.domain.BuyOrderComparator;
import com.sahana.ordermatching.domain.SellOrderComparator;
import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.order.Order;
import lombok.Getter;

import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OrderBook {

    public static final OrderBook ORDER_BOOK = new OrderBook();
    private ConcurrentHashMap<String, PriorityQueue<Order>> sellOrderBook;
    private ConcurrentHashMap<String, PriorityQueue<Order>> buyOrderBook;

    private OrderBook() {
        sellOrderBook = new ConcurrentHashMap<>();
        buyOrderBook = new ConcurrentHashMap<>();
    }

    public static OrderBook getOrderBook() {
        if (ORDER_BOOK != null) {
            return ORDER_BOOK;
        }
        OrderBook orderBookInstance = new OrderBook();
        return orderBookInstance;
    }


    public void addOrderToOrderBook(Order order) {
        if (order.getOrderSide() == OrderSide.BUY) {
            addBuyOrder(order);
        } else {
            addSellOrder(order);
        }
    }

    private void addSellOrder(Order order) {
        PriorityQueue<Order> sellOrderQueue = sellOrderBook.get(order.getStockName());
        if (sellOrderQueue != null) {
            sellOrderQueue.add(order);
        } else {
            sellOrderQueue = new PriorityQueue<>(new SellOrderComparator());
            sellOrderQueue.add(order);
            sellOrderBook.put(order.getStockName(), sellOrderQueue);
        }
    }

    private void addBuyOrder(Order order) {
        PriorityQueue<Order> buyOrderQueue = buyOrderBook.get(order.getStockName());
        if (buyOrderQueue != null) {
            buyOrderQueue.add(order);
        } else {
            buyOrderQueue = new PriorityQueue<>(new BuyOrderComparator());
            buyOrderQueue.add(order);
            buyOrderBook.put(order.getStockName(), buyOrderQueue);
        }
    }


}




