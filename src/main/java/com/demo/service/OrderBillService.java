package com.demo.service;
import com.demo.POJO.OrderBill;
import com.demo.wrapper.OrderBillWrapper;
import org.springframework.http.ResponseEntity;

public interface OrderBillService {
    ResponseEntity<String> generateAndSendBill(OrderBillWrapper wrapper);
}
