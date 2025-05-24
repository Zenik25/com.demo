package com.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

    private int tableNumber;                     // Required
    private List<String> orderedProductNames;    // Required
    private List<Integer> price;                 // Required
    private List<Integer> quantity;              // Required
    private int totalPrice;                      // Required
    private String status;                       // Optional (can default to "Confirming")
    private Integer shopId;
    private String orderType;
    private String email;
// Required
}

