package com.demo.dto;

import com.demo.POJO.OrderedProduct;
import com.demo.POJO.Delivery;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private String name;
    private String email;
    private String street;
    private String phone;
    private Integer shopId;
    private int totalAmount;
    private List<OrderedProduct> orderedProducts;
    private Delivery delivery; // Delivery details
    private String paymentMethod; // Payment method (Cash or KBZ Pay)

}
