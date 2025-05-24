package com.demo.POJO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class OrderBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tableNumber;

    @ElementCollection
    private List<String> orderedProductNames;

    @ElementCollection
    private List<Integer> quantity;

    @ElementCollection
    private List<Integer> price;

    private Integer totalPrice;

    private Integer taxAmount;

    private Integer netTotal;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate orderDate;

    @Column(name = "time", nullable = false, updatable = false)
    private LocalTime orderTime;

    private String email;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDate.now();
        this.orderTime = LocalTime.now();
    }

    // Getters and Setters
}