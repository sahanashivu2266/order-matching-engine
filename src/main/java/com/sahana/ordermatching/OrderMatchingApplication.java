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
import java.util.Scanner;

@Slf4j
public class OrderMatchingApplication {

    public static void main(String[] args) throws IOException {


        readFromConsoleAndMatchOrders();
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



    static void readFromConsoleAndMatchOrders() {
        Scanner sc = new Scanner(System.in);
        String input = "";
        final OrderMatchingService orderMatchingService = new OrderMatchingService();

        while (sc.hasNextLine()) {
            input = sc.nextLine();
            if(input.equals("END")) {
                log.info("End of input");
                break;
            }
            try {
                Order order = Parser.getParser().parseInput(input);
                orderMatchingService.processOrder(order);

            } catch (IllegalArgumentException e) {
                log.error("Error validating given input, {}", e.getMessage());
            } catch (UnsupportedOperationException e) {
                log.error("Provided operation (order type) is not supported yet!, {}", e.getMessage());
            }
        }
    }

}
