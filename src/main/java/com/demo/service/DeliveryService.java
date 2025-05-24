

package com.demo.service;
import com.demo.dto.PlaceOrderRequest;
import com.demo.POJO.Delivery;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface DeliveryService {
//    ResponseEntity<String> placeOrder(Delivery delivery);
    ResponseEntity<List<Delivery>> getAllOrders();
    ResponseEntity<Delivery> getOrderById(Long id);
    List<Delivery> getOrderByShop(Integer shopId);
    List<Delivery> getOrdersByEmail(String email);
//    String placeOrder(Delivery delivery, String paymentMethod);
    Delivery placeOrder(PlaceOrderRequest request);
    ResponseEntity<String> updateDeliveryStatus(Long id, String status);


    void save(Delivery delivery);
}
