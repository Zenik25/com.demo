package com.demo.service;

public interface QRCodeService {

    // Method to generate a QR code for the given table number
    void generateQRCode(String tableNumber, String seat, String password, Integer shopId) throws Exception;
}
