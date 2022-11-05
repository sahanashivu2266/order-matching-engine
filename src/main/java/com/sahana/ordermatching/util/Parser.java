package com.sahana.ordermatching.util;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.enums.OrderType;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
public class Parser {
    public static final Parser PARSER = new Parser();

    private Parser() {
    }

    public static Parser getParser() {
        if (PARSER != null) {
            return PARSER;
        }
        Parser parser = new Parser();
        return parser;
    }

    public Order parseInput(String input) throws IllegalArgumentException {

        String[] splitStr = input.split(" ");
        try {
            if (splitStr.length < 6) {
                throw new IllegalArgumentException("Number of arguments to place an order is not sufficient");
            }

            String orderId = splitStr[0];
            LocalTime orderPlacedTime = LocalTime.parse(splitStr[1]);
            String stockName = splitStr[2];
            int quantity = Integer.parseInt(splitStr[4]);
            float orderPrice = Float.parseFloat(splitStr[5]);

            OrderSide orderSide = getOrderSide(splitStr[3]);

            OrderBuilder orderBuilder = new OrderBuilder().
                    setOrderId(orderId)
                    .setTimeStamp(orderPlacedTime).
                    setStockName(stockName).
                    setQuantity(quantity).
                    setPrice(orderPrice).
                    setOrderSide(orderSide).setOrderType(OrderType.MARKET_ORDER);

            if (splitStr.length == 8) {
                populateLimitOrderProperties(splitStr, orderBuilder);
            }

            validateOrder(orderBuilder);
            return orderBuilder.createOrder();

        } catch (NumberFormatException e) {
            log.warn("Error while parsing the input {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.warn("Error while validating the input {}", e.getMessage());
            throw e;
        }


    }

    private void validateOrder(OrderBuilder order) throws IllegalArgumentException {
        if (order.getOrderSide() == OrderSide.UNKNOWN) {
            throw new IllegalArgumentException("Unknown order side, please mention buy or sell");
        }
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Order Quantity should be greater than 0");
        }
    }

    private void populateLimitOrderProperties(String[] splitStr, OrderBuilder orderBuilder) {
        orderBuilder.setOrderType(getOrderType(splitStr[6]));
        orderBuilder.setLimit(Float.parseFloat(splitStr[7]));
    }


    OrderSide getOrderSide(String orderSide) {
        switch (orderSide) {
            case "sell":
                return OrderSide.SELL;
            case "buy":
                return OrderSide.BUY;
            default:
                return OrderSide.UNKNOWN;
        }
    }

    OrderType getOrderType(String orderType) {
        switch (orderType) {
            case "limit":
                return OrderType.LIMIT_ORDER;
            default:
                return OrderType.MARKET_ORDER;
        }
    }

}
