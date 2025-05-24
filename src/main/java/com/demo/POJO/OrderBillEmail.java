package com.demo.POJO;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class OrderBillEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Integer tableNumber;

    @ElementCollection
    private List<String> orderedProductNames;

    @ElementCollection
    private List<Integer> quantity;

    @ElementCollection
    private List<Integer> price;

    private int totalPrice;
    private int taxAmount;
    private int netTotal;

    private LocalDate date;
    private LocalTime time;

    @PrePersist
    public void setDateTime() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }
}
