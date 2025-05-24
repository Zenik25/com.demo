package com.demo.wrapper;

import lombok.Data;

@Data
public class ProductWrapper {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String status;
    private Integer categoryId;
    private String categoryName;
    private String image;
    private Integer shopId;
    private String shopName;

    public ProductWrapper() {
        // Default constructor
    }

    // Constructor used by @NamedQuery
    public ProductWrapper(
            Integer id, String name, String description, Integer price,
            String status, Integer categoryId, String categoryName, String image, Integer shopId, String shopName
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = (status != null) ? status : "Unavailable";
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.image = image;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    // Optional constructors
    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
