package com.demo.dto;

import com.demo.POJO.Shop;

public class LoginResponse {
    private String email;
    private String role;
    private Shop shop;

    public LoginResponse(String email, String role, Shop shop) {
        this.email = email;
        this.role = role;
        this.shop = shop;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
