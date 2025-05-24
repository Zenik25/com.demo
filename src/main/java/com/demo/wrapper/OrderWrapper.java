package com.demo.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Data
//@AllArgsConstructor

public class OrderWrapper {
    private Long id;
    private int tableNumber;
    private List<String> orderedProductNames;
    private List<Integer> price;
    private List<Integer> quantity;
    private int totalPrice;
//    private Integer shopId;
//    private LocalDateTime orderDate;
    private LocalDate orderDate;  // Order Date from Delivery entity
    private LocalTime orderTime;
    private String status;

    // Constructor
    public OrderWrapper(Long id, int tableNumber, List<String> orderedProductNames, List<Integer> quantity, int totalPrice, LocalDate orderDate, LocalTime orderTime, String status, List<Integer> price) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.orderedProductNames = orderedProductNames;
        this.price= price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.status = status;
    }

    // Getters and Setters
}

