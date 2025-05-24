package com.demo.dto;

import com.demo.wrapper.ProductWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShopProductsResponse {
    private String shopName;
    private List<ProductWrapper> products;
}