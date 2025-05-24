//package com.demo.service;
//
//import com.demo.POJO.Bill;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Map;
//
//public interface BillService {
//
//    ResponseEntity<String> generateReport(Map<String,Object> requestMap);
//    ResponseEntity<List<Bill>> getBills();
//    ResponseEntity<byte[]> getPdf(Map<String,Object> requestMap);
//    ResponseEntity<String> deleteBill(Integer id);
//
//}
package com.demo.service;

import com.demo.POJO.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generateBillFromDelivery(Long deliveryId);
    ResponseEntity<byte[]> getBillPdf(String uuid);
    void generatePdf(String filePath, Bill bill) throws Exception;


}
