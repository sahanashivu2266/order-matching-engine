package com.sahana.ordermatching.executable;

import com.sahana.ordermatching.domain.BuyOrderComparator;
import com.sahana.ordermatching.domain.SellOrderComparator;
import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.order.OrderBuilder;
import com.sahana.ordermatching.util.OrderBook;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.PriorityQueue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMatchMarketOrder {

    static PriorityQueue<Order> sellOrderList;
    static PriorityQueue<Order> buyOrderList;

    static PriorityQueue<Order> buyOrderList1;
    static PriorityQueue<Order> hdfcSellOrderList1;
    static PriorityQueue<Order> hdfcBuyOrderList;
    static Order sellOrder1;
    static Order sellOrder2;
    static Order sellOrder3;
    static Order sellOrder4;
    static Order sellOrder5;
    static Order sellOrder6;
    static Order sellOrder7;
    static Order sellOrder8;

    static Order buyOrder1;
    static Order buyOrder2;
    static Order buyOrder3;
    static Order buyOrder4;
    static Order buyOrder5;
    static Order buyOrder6;
    static Order buyOrder7;

    @BeforeAll
    public static void beforeClass() {
        OrderBook.getOrderBook().getBuyOrderBook().clear();
        OrderBook.getOrderBook().getSellOrderBook().clear();

        sellOrderList = new PriorityQueue<>(new SellOrderComparator());
        buyOrderList = new PriorityQueue<>(new BuyOrderComparator());
        buyOrderList1 = new PriorityQueue<>(new BuyOrderComparator());
        hdfcSellOrderList1 = new PriorityQueue<>(new SellOrderComparator());
        hdfcBuyOrderList = new PriorityQueue<>(new BuyOrderComparator());


        OrderBook.getOrderBook().getBuyOrderBook().put("BAC", buyOrderList);
        OrderBook.getOrderBook().getSellOrderBook().put("BAC", sellOrderList);

        OrderBook.getOrderBook().getBuyOrderBook().put("TCS", buyOrderList1);
        OrderBook.getOrderBook().getSellOrderBook().put("HDFC", hdfcSellOrderList1);


        sellOrder1 = new OrderBuilder().setOrderId("#1").setPrice(240.1f).setQuantity(100).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();
        sellOrder2 = new OrderBuilder().setOrderId("#2").setPrice(237.45f).setQuantity(90).setTimeStamp(LocalTime.parse("09:45")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();
        sellOrder3 = new OrderBuilder().setOrderId("#5").setPrice(241.50f).setQuantity(220).setTimeStamp(LocalTime.parse("09:48")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();
        sellOrder4 = new OrderBuilder().setOrderId("#8").setPrice(240.10f).setQuantity(20).setTimeStamp(LocalTime.parse("10:01")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();
        sellOrder5 = new OrderBuilder().setOrderId("#10").setPrice(237.10f).setQuantity(40).setTimeStamp(LocalTime.parse("10:10")).setOrderSide(OrderSide.SELL).setStockName("BAC").createOrder();
        sellOrder6 = new OrderBuilder().setOrderId("#12").setPrice(237.10f).setQuantity(40).setTimeStamp(LocalTime.parse("10:10")).setOrderSide(OrderSide.SELL).setStockName("HDFC").createOrder();
        sellOrder7 = new OrderBuilder().setOrderId("#12").setPrice(210.10f).setQuantity(100).setTimeStamp(LocalTime.parse("10:30")).setOrderSide(OrderSide.SELL).setStockName("HDFC").createOrder();
        sellOrder8 = new OrderBuilder().setOrderId("#15").setPrice(280.10f).setQuantity(100).setTimeStamp(LocalTime.parse("10:35")).setOrderSide(OrderSide.SELL).setStockName("HDFC").createOrder();

        buyOrder1 = new OrderBuilder().setOrderId("#3").setPrice(238.10f).setQuantity(80).setTimeStamp(LocalTime.parse("09:47")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        buyOrder2 = new OrderBuilder().setOrderId("#6").setPrice(238.50f).setQuantity(50).setTimeStamp(LocalTime.parse("09:49")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        buyOrder3 = new OrderBuilder().setOrderId("#7").setPrice(1001.10f).setQuantity(10).setTimeStamp(LocalTime.parse("09:52")).setOrderSide(OrderSide.BUY).setStockName("TCS").createOrder();
        buyOrder4 = new OrderBuilder().setOrderId("#9").setPrice(242.70f).setQuantity(150).setTimeStamp(LocalTime.parse("10:02")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        buyOrder5 = new OrderBuilder().setOrderId("#11").setPrice(242.70f).setQuantity(190).setTimeStamp(LocalTime.parse("10:10")).setOrderSide(OrderSide.BUY).setStockName("BAC").createOrder();
        buyOrder6 = new OrderBuilder().setOrderId("#13").setPrice(220.10f).setQuantity(40).setTimeStamp(LocalTime.parse("10:20")).setOrderSide(OrderSide.BUY).setStockName("HDFC").createOrder();
        buyOrder7 = new OrderBuilder().setOrderId("#14").setPrice(300.10f).setQuantity(150).setTimeStamp(LocalTime.parse("10:31")).setOrderSide(OrderSide.BUY).setStockName("HDFC").createOrder();
    }


    @AfterAll
    public static void afterTests() {
        buyOrderList.clear();
        sellOrderList.clear();
        buyOrderList1.clear();

        hdfcBuyOrderList.clear();
        hdfcSellOrderList1.clear();
        OrderBook.getOrderBook().getBuyOrderBook().clear();
        OrderBook.getOrderBook().getSellOrderBook().clear();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testProcessOrder_Complete_Buy_Order_Match_BO_Less_Than_SO() {
        sellOrderList.add(sellOrder1);
        sellOrderList.add(sellOrder2);

        MatchMarketOrder marketOrder = new MatchMarketOrder(buyOrder1);

        marketOrder.processOrder(buyOrder1);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(2, sellOrderBook.size());
        Assert.assertEquals(0, buyOrderBook.size());
    }


    @Test
    @org.junit.jupiter.api.Order(2)
    public void testMatchSellOrder_Buy_Order_Not_Present() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(sellOrder3);

        marketOrder.processOrder(sellOrder3);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(3, sellOrderBook.size());
        Assert.assertEquals(0, buyOrderBook.size());
        Assert.assertEquals(220, sellOrder3.getQuantity());
    }


    @Test
    @org.junit.jupiter.api.Order(3)
    public void testProcessOrder_Partial_Buy_Order_Match() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(buyOrder2);

        marketOrder.processOrder(buyOrder2);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(2, sellOrderBook.size());
        Assert.assertEquals(1, buyOrderBook.size());
        Assert.assertEquals(40, buyOrder2.getQuantity());
    }


    @Test
    @org.junit.jupiter.api.Order(4)
    public void testProcessOrder_No_Sell_Order_Match() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(buyOrder3);

        marketOrder.processOrder(buyOrder3);

        PriorityQueue<Order> tcsBuyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("TCS");

        Assert.assertEquals(1, tcsBuyOrderBook.size());
        Assert.assertEquals(10, buyOrder3.getQuantity());
    }


    @Test
    @org.junit.jupiter.api.Order(5)
    public void testMatchSellOrder_No_Buy_Order_Match() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(sellOrder4);

        marketOrder.processOrder(sellOrder4);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(3, sellOrderBook.size());
        Assert.assertEquals(1, buyOrderBook.size());
        Assert.assertEquals(20, sellOrder4.getQuantity());
    }


    @Test
    @org.junit.jupiter.api.Order(6)
    public void testProcessOrder_Partial_Sell_Order_Match() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(buyOrder4);

        marketOrder.processOrder(buyOrder4);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(1, sellOrderBook.size());
        Assert.assertEquals(1, buyOrderBook.size());
    }


    @Test
    @org.junit.jupiter.api.Order(7)
    public void testProcessOrder_Complete_Sell_Order_Match() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(sellOrder5);

        marketOrder.processOrder(sellOrder5);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(1, sellOrderBook.size());
        Assert.assertEquals(0, buyOrderBook.size());
    }


    @Test
    @org.junit.jupiter.api.Order(8)
    public void testProcessOrder_Complete_Buy_Order_Match() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(buyOrder5);

        marketOrder.processOrder(buyOrder5);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("BAC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("BAC");

        Assert.assertEquals(0, sellOrderBook.size());
        Assert.assertEquals(0, buyOrderBook.size());
    }


    @Test
    @org.junit.jupiter.api.Order(9)
    public void testProcessOrder_No_BO_Present_For_Stock() {
        MatchMarketOrder marketOrder = new MatchMarketOrder(sellOrder6);

        marketOrder.processOrder(sellOrder6);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("HDFC");

        Assert.assertEquals(1, sellOrderBook.size());
        Assert.assertEquals(false, OrderBook.getOrderBook().getBuyOrderBook().containsKey("HDFC"));
    }


    @Test
    @org.junit.jupiter.api.Order(10)
    public void testProcessOrder_Partial_SO_Match_SO_Greater_Than_BO() {
        OrderBook.getOrderBook().getBuyOrderBook().put("HDFC", hdfcBuyOrderList);
        hdfcBuyOrderList.add(buyOrder6);

        MatchMarketOrder marketOrder = new MatchMarketOrder(sellOrder7);

        marketOrder.processOrder(sellOrder7);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("HDFC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("HDFC");

        Assert.assertEquals(2, sellOrderBook.size());
        Assert.assertEquals(0, buyOrderBook.size());
    }


    @Test
    @org.junit.jupiter.api.Order(11)
    public void testProcessOrder_Partial_SO_Match_SO_Less_Than_BO() {
        hdfcBuyOrderList.add(buyOrder7);
        MatchMarketOrder marketOrder = new MatchMarketOrder(sellOrder8);

        marketOrder.processOrder(sellOrder8);

        PriorityQueue<Order> sellOrderBook = OrderBook.getOrderBook().getSellOrderBook().get("HDFC");
        PriorityQueue<Order> buyOrderBook = OrderBook.getOrderBook().getBuyOrderBook().get("HDFC");

        Assert.assertEquals(2, sellOrderBook.size());
        Assert.assertEquals(1, buyOrderBook.size());
        Assert.assertEquals(50, buyOrder7.getQuantity());
    }
}
