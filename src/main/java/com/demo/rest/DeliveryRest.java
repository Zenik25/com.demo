//package com.demo.rest;
//
//import com.demo.POJO.Delivery;
//import com.demo.wrapper.DeliveryWrapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequestMapping("/deliveries")
//public interface DeliveryRest {
//    @PostMapping("/add")
//    ResponseEntity<Delivery> placeOrder(@RequestBody Delivery delivery);
//
//    @GetMapping("/get")
//    ResponseEntity<List<Delivery>> getAllOrders();
//
//    @GetMapping("/email/{email}")
//    ResponseEntity<List<DeliveryWrapper>> getOrdersByEmail(@PathVariable String email);
//}


package com.demo.rest;

import com.demo.POJO.Delivery;
import com.demo.dto.PlaceOrderRequest;
import com.demo.dto.ShopDeliveryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*") // Allow all origins


@RequestMapping("/delivery")
public interface DeliveryRest {


    @PostMapping("/placeOrder")
    Delivery placeOrder(@RequestBody PlaceOrderRequest request);
    @GetMapping("/all")
    ResponseEntity<List<Delivery>> getAllOrders();

    @GetMapping("/{id}")
    ResponseEntity<Delivery> getOrderById(@PathVariable Long id);

    @PutMapping("/status/{id}")
    ResponseEntity<String> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status);

    @GetMapping("/getOrderByShop/{shopId}")
    ResponseEntity<ShopDeliveryResponse> getOrderByShop(@PathVariable Integer shopId);




}
