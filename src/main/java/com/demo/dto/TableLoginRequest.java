package com.demo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class TableLoginRequest {
    private String tableNumber;
    private String seat;
    private String password;
    private Integer shopId;
    private String status;
    // Constructors
    public TableLoginRequest() {}

    public TableLoginRequest(String tableNumber, String seat, String password, Integer shopId) {
        this.tableNumber = tableNumber;
        this.seat = seat;
        this.password = password;
        this.shopId = shopId;
    }

//    // Getters and Setters
//    public String getTableNumber() {
//        return tableNumber;
//    }
//
//    public void setTableNumber(String tableNumber) {
//        this.tableNumber = tableNumber;
//    }
//
//    public String getSeat() {
//        return seat;
//    }
//
//    public void setSeat(String seat) {
//        this.seat = seat;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}

