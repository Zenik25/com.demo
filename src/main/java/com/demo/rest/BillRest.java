//package com.demo.rest;
//
//import com.demo.POJO.Bill;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RequestMapping(path = "/bill")
//public interface BillRest {
//
//    @PostMapping(path = "/generateReport")
//    ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);
//
//    @GetMapping(path = "/getBills")
//    ResponseEntity<List<Bill>> getBills();
//
//    @PostMapping(path = "/getPdf")
//    ResponseEntity<byte[]> getPdf(@RequestBody Map<String,Object> requestMap);
//
//    @PostMapping(path = "/delete/{id}")
//    ResponseEntity<String> deleteBill(@PathVariable Integer id);
//
//}
package com.demo.rest;

import com.demo.POJO.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface BillRest {
    ResponseEntity<String> generateBillFromDelivery(Long deliveryId);
    ResponseEntity<byte[]> getBillPdf(String uuid);
}


