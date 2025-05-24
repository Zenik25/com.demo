//package com.demo.restlmpl;
//
//import com.demo.rest.QRCodeRest;
//import com.demo.servicelmpl.QRCodeServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@RestController
//@RequestMapping("/table-login")  // Mapping the API to /table-login
//public class QRCodeRestImpl implements QRCodeRest {
//
//    @Autowired
//    private QRCodeServiceImpl qrCodeServiceImpl;
//
//    @Override
//    public ResponseEntity<String> generateQRCode(String tableNumber) throws Exception {
//        String filePath = qrCodeServiceImpl.generateQRCode(tableNumber);
//
//        if (filePath != null) {
//            return new ResponseEntity<>("QR Code generated and saved at: " + filePath, HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Failed to generate QR code", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}

package com.demo.restlmpl;

import com.demo.rest.QRCodeRest;
import com.demo.servicelmpl.QRCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping("/table-login")  // Mapping the API to /table-login
public class QRCodeRestImpl implements QRCodeRest {

    @Autowired
    private QRCodeServiceImpl qrCodeServiceImpl;

    @Override
    public ResponseEntity<String> generateQRCode(@RequestParam String tableNumber, String seat, String password, Integer shopId) throws Exception {
        qrCodeServiceImpl.generateQRCode(tableNumber,seat,password,shopId); // This will generate the QR code

        return new ResponseEntity<>("QR Code generated successfully for table number: " + tableNumber, HttpStatus.OK);
    }
}
