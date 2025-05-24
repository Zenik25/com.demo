package com.demo.wrapper;

import lombok.Data;

import java.util.List;
@Data
public class ShopTableWrapper {
    private Integer shopId;
    private String shopName;
    private List<TableLoginWrapper> tables;

    // Getters and Setters
}
