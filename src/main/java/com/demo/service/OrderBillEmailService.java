package com.demo.service;

import com.demo.POJO.OrderBill;
import com.demo.wrapper.OrderBillWrapper;
import org.springframework.http.ResponseEntity;

public interface OrderBillEmailService {
    void sendOrderBillEmail(OrderBill bill);
    ResponseEntity<String> generateAndSendBill(OrderBillWrapper wrapper); // Add this

}
