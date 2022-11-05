package com.sahana.ordermatching;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

class TestOrderMatchingApplication {

    @Test
    void testOrderMatchingApplication_Invalid_File() {
        Exception e = Assertions.assertThrows(FileNotFoundException.class, () -> OrderMatchingApplication.readAndMatchOrders("order.txt"));

        String expectedStr = "(No such file or directory)";
        Assertions.assertTrue(e.getMessage().contains(expectedStr));

    }


    @Test
    void testOrderMatchingApplication() {
        Assertions.assertDoesNotThrow(() -> OrderMatchingApplication.readAndMatchOrders("src/test/resources/orders.txt"));
    }


    @Test
    void testOrderMatchingApplication_parsing_error() {
        Assertions.assertDoesNotThrow(() -> OrderMatchingApplication.readAndMatchOrders("src/test/resources/parsing_error.txt"));
    }

    @Test
    void testOrderMatchingApplication_validation_error() {
        Assertions.assertDoesNotThrow(() -> OrderMatchingApplication.readAndMatchOrders("src/test/resources/validation_error.txt"));
    }

    @Test
    void testOrderMatchingApplication_limit_order_but_not_supported() {
        Assertions.assertDoesNotThrow(() -> OrderMatchingApplication.readAndMatchOrders("src/test/resources/limit_orders_included.txt"));
    }
}
