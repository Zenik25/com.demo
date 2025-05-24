package com.demo.restlmpl;

import com.demo.POJO.OrderBill;
import com.demo.rest.OrderBillRest;
import com.demo.service.OrderBillService;
import com.demo.wrapper.OrderBillWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-bill")
public class OrderBillRestImpl implements OrderBillRest {

    @Autowired
    private OrderBillService orderBillService;

//    @Override
    @PostMapping("/generate")
    public ResponseEntity<String> generateAndSendBill(@RequestBody OrderBillWrapper wrapper) {
        return orderBillService.generateAndSendBill(wrapper);
    }
}
