package com.sahana.ordermatching.executable;

import com.sahana.ordermatching.domain.OrderTransaction;
import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.util.OrderBook;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;

@Slf4j
@AllArgsConstructor
public class MatchMarketOrder implements OrderStrategy {

    Order order;

    @Override
    public void processOrder(Order order) {
        if (order.getOrderSide() == OrderSide.BUY) {
            matchBuyOrder(order);
        } else {
            matchSellOrder(order);
        }
    }

    private void matchSellOrder(Order sellOrder) {
        OrderBook orderBook = OrderBook.getOrderBook();

        if (!orderBook.getBuyOrderBook().containsKey(sellOrder.getStockName())) {
            log.info("No available buy orders present for stock {}", sellOrder.getStockName());
            OrderBook.getOrderBook().addOrderToOrderBook(sellOrder); //for next processing
            return;
        }
        PriorityQueue<Order> buyOrdersList = orderBook.getBuyOrderBook().get(sellOrder.getStockName());
        PriorityQueue<Order> sellOrdersList = orderBook.getSellOrderBook().get(sellOrder.getStockName());

        while (buyOrdersList.size() > 0 && buyOrdersList.peek().getPrice() - sellOrder.getPrice() >= 0) {
            Order matchedBuyOrder = buyOrdersList.poll();

            if (matchedBuyOrder.getQuantity() <= sellOrder.getQuantity()) { //quantity can't be zero, as everytime we update the quantity after match
                executePartialSellOrder(matchedBuyOrder, sellOrder, buyOrdersList, sellOrdersList);
            } else {
                executeCompleteSellOrder(matchedBuyOrder, sellOrder, buyOrdersList, sellOrdersList);
                buyOrdersList.add(matchedBuyOrder);
            }

            if (sellOrder.getQuantity() == 0)
                return;

        }

        if (sellOrder.getQuantity() > 0) {
            OrderBook.getOrderBook().addOrderToOrderBook(sellOrder); //for next processing
        }


    }

    private void matchBuyOrder(Order buyOrder) {
        OrderBook orderBook = OrderBook.getOrderBook();

        if (!orderBook.getSellOrderBook().containsKey(buyOrder.getStockName())) {
            log.info("No available sell orders present for stock {}", buyOrder.getStockName());
            OrderBook.getOrderBook().addOrderToOrderBook(buyOrder); //for next processing
            return;
        }
        PriorityQueue<Order> buyOrdersList = orderBook.getBuyOrderBook().get(buyOrder.getStockName());
        PriorityQueue<Order> sellOrdersList = orderBook.getSellOrderBook().get(buyOrder.getStockName());

        while (sellOrdersList.size() > 0 && buyOrder.getPrice() - sellOrdersList.peek().getPrice() >= 0) {
            Order matchedSellOrder = sellOrdersList.poll();

            if (matchedSellOrder.getQuantity() <= buyOrder.getQuantity()) { //quantity can't be zero, as everytime we update the quantity after match
                executePartialBuyOrder(buyOrder, matchedSellOrder, buyOrdersList, sellOrdersList);
            } else {
                executeCompleteBuyOrder(buyOrder, matchedSellOrder, buyOrdersList, sellOrdersList);
                sellOrdersList.add(matchedSellOrder);
            }
            if (buyOrder.getQuantity() == 0)
                return;

        }

        if (buyOrder.getQuantity() > 0) {
            OrderBook.getOrderBook().addOrderToOrderBook(buyOrder); //for next processing
        }

    }

    private void executeCompleteBuyOrder(Order buyOrder, Order matchedSellOrder, PriorityQueue<Order> buyOrdersList, PriorityQueue<Order> sellOrdersList) {
        log.debug("Executing complete buy order match, buyOrder {}, sellOrder {}", buyOrder, matchedSellOrder);
        int soldQuantity = buyOrder.getQuantity();

        matchedSellOrder.setQuantity(matchedSellOrder.getQuantity() - buyOrder.getQuantity());
        buyOrder.setQuantity(0);

        OrderTransaction orderPlaced = OrderTransaction.logOrderTransaction(matchedSellOrder, buyOrder, soldQuantity);
        log.info("{}", orderPlaced);

    }

    private void executePartialBuyOrder(Order buyOrder, Order matchedSellOrder, PriorityQueue<Order> buyOrdersList, PriorityQueue<Order> sellOrdersList) {
        log.debug("Executing partial buy order match, buyOrder {}, sellOrder {}", buyOrder, matchedSellOrder);
        OrderTransaction orderPlaced = OrderTransaction.logOrderTransaction(matchedSellOrder, buyOrder, matchedSellOrder.getQuantity());
        log.info("{}", orderPlaced);

        buyOrder.setQuantity(buyOrder.getQuantity() - matchedSellOrder.getQuantity());

    }


    private void executeCompleteSellOrder(Order matchedBuyOrder, Order sellOrder, PriorityQueue<Order> buyOrdersList, PriorityQueue<Order> sellOrdersList) {
        log.debug("Executing complete sell order match, buyOrder {}, sellOrder {}", matchedBuyOrder, sellOrder);
        int soldQuantity = sellOrder.getQuantity();

        matchedBuyOrder.setQuantity(matchedBuyOrder.getQuantity() - sellOrder.getQuantity());
        sellOrder.setQuantity(0);

        OrderTransaction orderPlaced = OrderTransaction.logOrderTransaction(sellOrder, matchedBuyOrder, soldQuantity);
        log.info("{}", orderPlaced);

    }


    private void executePartialSellOrder(Order matchedBuyOrder, Order sellOrder, PriorityQueue<Order> buyOrdersList, PriorityQueue<Order> sellOrdersList) {
        log.debug("Executing partial sell order match, buyOrder {}, sellOrder {}", matchedBuyOrder, sellOrder);
        OrderTransaction orderPlaced = OrderTransaction.logOrderTransaction(sellOrder, matchedBuyOrder, matchedBuyOrder.getQuantity());
        log.info("{}", orderPlaced);

        sellOrder.setQuantity(sellOrder.getQuantity() - matchedBuyOrder.getQuantity());


    }


}
