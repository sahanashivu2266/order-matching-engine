package com.sahana.ordermatching;

import com.sahana.ordermatching.order.Order;
import com.sahana.ordermatching.service.OrderMatchingService;
import com.sahana.ordermatching.util.OrderBook;
import com.sahana.ordermatching.util.Parser;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class OrderMatchingApplication {

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/input/input.txt"))) {
            String file;
            while ((file = bufferedReader.readLine()) != null) {
                log.info("file is " + file);
                clearOrderBook(); //clear orderBook each time new input file is read, comment it, if u want to process on previous orders.
                readAndMatchOrders(file);
            }
        } catch (IOException e) {
            log.error("Error while reading the file, {}", e.getMessage());
        }
    }

    private static void clearOrderBook() {
        OrderBook.getOrderBook().getSellOrderBook().clear();
        OrderBook.getOrderBook().getBuyOrderBook().clear();
    }


    static void readAndMatchOrders(String filePath) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            log.info("Reading input file {}", filePath);

            String line;
            final OrderMatchingService orderMatchingService = new OrderMatchingService();
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    Order order = Parser.getParser().parseInput(line);
                    orderMatchingService.processOrder(order);

                } catch (IllegalArgumentException e) {
                    log.error("Error validating given input, {}", e.getMessage());
                } catch (UnsupportedOperationException e) {
                    log.error("Provided operation (order type) is not supported yet!, {}", e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Invalid file path!, exception {}, exiting", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception while processing the orders {}, exiting", e.getMessage());
            throw e;
        }
    }

}
