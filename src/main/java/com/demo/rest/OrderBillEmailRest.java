package com.demo.rest;

import com.demo.wrapper.OrderBillWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order-bill-email")
public interface OrderBillEmailRest {
    @PostMapping("/send")
    ResponseEntity<String> sendOrderBillEmail(@RequestBody OrderBillWrapper wrapper);
}
