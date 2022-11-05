package com.sahana.ordermatching.factory;

import com.sahana.ordermatching.executable.MatchMarketOrder;
import com.sahana.ordermatching.executable.OrderStrategy;
import com.sahana.ordermatching.order.Order;

public class OrderMatchingFactory {

    public static OrderStrategy getExecutableThroughOrderType(Order order) {

        switch (order.getOrderType()) {
            case LIMIT_ORDER:
                throw new UnsupportedOperationException("Limit order is not supported yet"); //todo handle this
            default:
                return new MatchMarketOrder(order);
        }

    }
}
