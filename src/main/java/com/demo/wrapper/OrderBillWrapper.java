package com.demo.wrapper;

import lombok.Data;

import java.util.List;
@Data
public class OrderBillWrapper {
    private Integer tableNumber;
    private List<String> orderedProductNames;
    private List<Integer> quantity;
    private List<Integer> price;
    private Integer totalPrice;
    private Integer taxAmount;
    private Integer netTotal;
    private Integer shopId;
    private String email;
}