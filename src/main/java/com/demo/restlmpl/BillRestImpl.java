//package com.demo.restlmpl;
//
//import com.demo.POJO.Bill;
//import com.demo.constents.ResConstants;
//import com.demo.rest.BillRest;
//import com.demo.service.BillService;
//import com.demo.utils.ResUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class BillRestImpl implements BillRest {
//
//    @Autowired
//    BillService billService;
//
//    @Override
//    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
//        try {
//            return billService.generateReport(requestMap);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Override
//    public ResponseEntity<List<Bill>> getBills() {
//        try{
//            return billService.getBills();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
//        try {
//            return billService.getPdf(requestMap);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<String> deleteBill(Integer id) {
//        try{
//            return billService.deleteBill(id);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//}
package com.demo.restlmpl;

import com.demo.POJO.Bill;
import com.demo.POJO.Delivery;
import com.demo.constents.ResConstants;
import com.demo.rest.BillRest;
import com.demo.service.BillService;
import com.demo.service.EmailService;
import com.demo.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillRestImpl implements BillRest {

    @Autowired
    private BillService billService;

    @Override
    @PostMapping("/generate/{deliveryId}")
    public ResponseEntity<String> generateBillFromDelivery(@PathVariable Long deliveryId) {
        return billService.generateBillFromDelivery(deliveryId);
    }

    @Override
    @GetMapping("/pdf/{uuid}")
    public ResponseEntity<byte[]> getBillPdf(@PathVariable String uuid) {
        return billService.getBillPdf(uuid);
    }
}

