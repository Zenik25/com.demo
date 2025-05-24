package com.demo.dto;

import com.demo.POJO.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ShopDeliveryResponse {
    private String shopName;
    private List<Delivery> deliveries;

//    public ShopDeliveryResponse(String shopName, List<Delivery> deliveries) {
//        this.shopName = shopName;
//        this.deliveries = deliveries;
//    }


}
