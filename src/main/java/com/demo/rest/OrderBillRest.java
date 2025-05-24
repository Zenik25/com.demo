package com.demo.rest;

import com.demo.POJO.OrderBill;
import com.demo.wrapper.OrderBillWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-bill")
public interface OrderBillRest {

    @PostMapping("/generate")
    ResponseEntity<String> generateAndSendBill(@RequestBody OrderBillWrapper wrapper);
}
