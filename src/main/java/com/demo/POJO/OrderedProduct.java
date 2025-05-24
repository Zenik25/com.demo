package com.demo.POJO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ordered_products")
@Data
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private int quantity;


    private Integer price;
    // Many ordered products belong to one delivery
    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    @JsonBackReference  // Prevent infinite recursion
    private Delivery delivery;
    @Column(name = "product_id")
    private Integer productId;

//    private Pr product;
//    public Integer getPrice() {
//        return product.getPrice();  // Assuming Product has a getPrice() method
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;  // Setter for price
//    }

}
