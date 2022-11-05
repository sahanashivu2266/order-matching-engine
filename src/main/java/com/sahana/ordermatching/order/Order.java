package com.sahana.ordermatching.order;

import com.sahana.ordermatching.enums.OrderSide;
import com.sahana.ordermatching.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @NonNull
    private String orderId;

    @NonNull
    private OrderSide orderSide;

    @NonNull
    private float price;

    @NonNull
    private String stockName;

    @NonNull
    private int quantity;

    @NonNull
    private LocalTime timeStamp;

    private OrderType orderType;

    private float limit;

    @Override
    public String toString() {
        return this.getOrderId() + " " + this.getTimeStamp() + " " + this.getStockName() + " " + this.getOrderSide() +
                " " + this.getQuantity() + " " + this.getPrice();
    }

}
