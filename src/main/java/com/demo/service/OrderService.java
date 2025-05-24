package com.demo.service;

import com.demo.POJO.Order;
import com.demo.dto.OrderRequest;
import com.demo.wrapper.OrderWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface OrderService {
//    ResponseEntity<String> placeOrder(Map<String, Object> requestMap);
ResponseEntity<String> placeOrder(OrderRequest orderRequest);

    List<OrderWrapper> getAllOrders();

    List<Order> getOrderByShop(Integer shopId);

    ResponseEntity<String> updateOrderStatus(Long id, String status);

}
