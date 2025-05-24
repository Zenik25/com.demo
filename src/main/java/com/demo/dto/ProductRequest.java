package com.demo.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Integer price;
    private Integer categoryId;
    private String image;  // This should be the image file name stored on backend
}
