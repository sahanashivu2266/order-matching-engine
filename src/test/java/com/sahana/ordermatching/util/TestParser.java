package com.sahana.ordermatching.util;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.enums.OrderType;
import com.sahana.ordermatching.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class TestParser {

    @Test
    void testSingletonParser() {
        Parser parser1 = Parser.getParser();
        Parser parser2 = Parser.getParser();

        Assertions.assertEquals(parser1, parser2);
    }


    @Test
    void testParseInput_Invalid_Input_Len() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Parser.getParser().parseInput(""));

        String expectedMsg = "Number of arguments to place an order is not sufficient";

        Assertions.assertEquals(expectedMsg, exception.getMessage());
    }


    @Test
    void testParseInput_Valid_Input() {
        Order order = Parser.getParser().parseInput("#1 09:45 BAC sell 100 240.10");

        Assertions.assertEquals(order.getOrderType(), OrderType.MARKET_ORDER);
        Assertions.assertEquals(order.getOrderId(), "#1");
        Assertions.assertEquals(order.getOrderSide(), OrderSide.SELL);
        Assertions.assertEquals(LocalTime.parse("09:45"), order.getTimeStamp());
    }


    @Test
    void testParseInput_Valid_Input_LIMIT_ORDER() {
        Order order = Parser.getParser().parseInput("#1 09:45 BAC sell 100 240.10 limit 90");

        Assertions.assertEquals("#1", order.getOrderId());
        Assertions.assertEquals(OrderSide.SELL, order.getOrderSide());
        Assertions.assertEquals(LocalTime.parse("09:45"), order.getTimeStamp());

        Assertions.assertEquals(OrderType.LIMIT_ORDER, order.getOrderType());
        Assertions.assertEquals(90, order.getLimit(), 0.0001);

    }


    @Test
    void testParseInput_InValid_Input_LIMIT_ORDER_Parsing_exception() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Parser.getParser().parseInput("#1 09:45 BAC buy 100 240.10 limit li");
        });

        String expectedMsg = "For input string: \"li\"";

        Assertions.assertTrue(exception.getMessage().contains(expectedMsg));
    }


    @Test
    void testParseInput_InValid_Input_LIMIT_ORDER_Dif_Order_Type() {
        Order order = Assertions.assertDoesNotThrow(() -> Parser.getParser().parseInput("#1 09:45 BAC buy 100 240.10 fifo 90"));

        Assertions.assertEquals(OrderType.MARKET_ORDER, order.getOrderType());
    }


    @Test
    void testParseInput_InValid_Input_Parsing_Exception_Quantity() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Parser.getParser().parseInput("#1 09:45 BAC sell 1.1 240.10");
        });

        String expectedMsg = "For input string: \"1.1\"";

        Assertions.assertTrue(exception.getMessage().contains(expectedMsg));
    }

    @Test
    void testParseInput_Price_In_Integer() {
        Order order = Assertions.assertDoesNotThrow(() -> Parser.getParser().parseInput("#1 09:45 BAC buy 100 240"));

        Assertions.assertEquals(240, order.getPrice(), 0.001);
    }


    @Test
    void testParseInput_InValid_Input_Parsing_Exception_Price() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Parser.getParser().parseInput("#1 09:45 BAC sell 100 240h");
        });

        String expectedMsg = "For input string: \"240h\"";

        Assertions.assertTrue(exception.getMessage().contains(expectedMsg));
    }


    @Test
    void testValidateOrder_Invalid_OrderSide() {
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> Parser.getParser().parseInput("#1 09:45 BAC so 100 240"));

        String expectedMsg = "Unknown order side, please mention buy or sell";
        Assertions.assertTrue(e.getMessage().contains(expectedMsg));
    }


    @Test
    void testValidateOrder_Invalid_Quantity() {
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> Parser.getParser().parseInput("#1 09:45 BAC sell -1 240"));

        String expectedMsg = "Order Quantity should be greater than 0";
        Assertions.assertTrue(e.getMessage().contains(expectedMsg));
    }
}



