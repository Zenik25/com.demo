package com.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//@CrossOrigin(origins = "*") // Allow all origins
public interface QRCodeRest {

    // Define the REST API for generating the QR code
    @GetMapping("/generate-qr/{tableNumber}")
    ResponseEntity<String> generateQRCode(@PathVariable("tableNumber") String tableNumber, String seat, String password, Integer shopId) throws Exception;
}
