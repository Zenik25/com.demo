package com.demo.POJO;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderedItem {

    private String name;

    private int quantity;

    private int price; // price per unit

    // Constructors
    public OrderedItem() {}

    public OrderedItem(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}

