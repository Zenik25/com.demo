package com.demo.rest;

import com.demo.POJO.Order;
import com.demo.dto.OrderRequest;
import com.demo.wrapper.OrderWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.service.OrderService;
import java.util.List;
import java.util.Map;
//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping("/orders")
public interface OrderRest {
    @PostMapping("/placeOrder")
    ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest);

    @GetMapping("/get")
    ResponseEntity<List<OrderWrapper>> getAllOrders();

    @GetMapping("/getByShopId/{shopId}")
    ResponseEntity<List<Order>> getOrderByShop(@PathVariable Integer shopId);


    @PutMapping("/status/{id}")
    ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestParam String status);

}
