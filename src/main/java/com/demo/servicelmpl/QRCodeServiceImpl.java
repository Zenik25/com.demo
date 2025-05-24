////package com.demo.servicelmpl;
////
////import com.demo.service.QRCodeService;
////import com.google.zxing.BarcodeFormat;
////import com.google.zxing.MultiFormatWriter;
////import com.google.zxing.WriterException;
////import com.google.zxing.common.BitMatrix;
////import org.springframework.stereotype.Service;
////
////import javax.imageio.ImageIO;
////import java.awt.*;
////import java.awt.image.BufferedImage;
////import java.io.File;
////import java.io.IOException;
////
////@Service
////public class QRCodeServiceImpl implements QRCodeService {
////
////    private static final String QR_CODE_PATH = "./qrcodes/";
////
////    @Override
////    public void generateQRCode(String tableNumber, String seat, String password) throws Exception {
////        // Combine all necessary information into a single string
////        String data = "tableNumber=" + tableNumber + "&seat=" + seat + "&password=" + password;
////
////
////        // Ensure the directory exists
////        File directory = new File(QR_CODE_PATH);
////        if (!directory.exists()) {
////            if (directory.mkdirs()) {
////                System.out.println("Directory created at: " + QR_CODE_PATH);
////            } else {
////                System.err.println("Failed to create directory at: " + QR_CODE_PATH);
////            }
////        }
////
////        // Path to save the generated QR code
////        String filePath = QR_CODE_PATH + tableNumber + "-qr.png"; // Path to save the QR code
////        generateQRCodeImage(data, 200, 200, filePath); // Generate and save the QR code
////    }
////
////
////    private void generateQRCodeImage(String data, int width, int height, String filePath) throws WriterException, IOException {
////        // Use ZXing library to generate QR code
////        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
////        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
////
////        // Convert BitMatrix to BufferedImage
////        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
////        image.createGraphics();
////
////        Graphics2D graphics = (Graphics2D) image.getGraphics();
////        graphics.setColor(Color.WHITE);
////        graphics.fillRect(0, 0, width, height);
////        graphics.setColor(Color.BLACK);
////
////        // Draw the QR code on the image
////        for (int i = 0; i < width; i++) {
////            for (int j = 0; j < height; j++) {
////                if (bitMatrix.get(i, j)) {
////                    image.setRGB(i, j, Color.BLACK.getRGB());
////                }
////            }
////        }
////
////        // Write the image to a file
////        File qrFile = new File(filePath);
////        ImageIO.write(image, "PNG", qrFile);
////
////        System.out.println("QR code generated and saved at: " + filePath);
////    }
////}
//package com.demo.servicelmpl;
//
//import com.demo.service.QRCodeService;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import org.springframework.stereotype.Service;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//@Service
//public class QRCodeServiceImpl implements QRCodeService {
//
//    private static final String QR_CODE_PATH = "./qrcodes/";
//
//    @Override
//    public void generateQRCode(String tableNumber, String seat, String password) throws Exception {
//
//
//        String qrContent = "http://localhost:5173/auto-login?tableNumber=" + tableNumber + "&seat=" + seat + "&password=" + password;
//
//        // Ensure the directory exists
//        File directory = new File(QR_CODE_PATH);
//        if (!directory.exists()) {
//            if (directory.mkdirs()) {
//                System.out.println("Directory created at: " + QR_CODE_PATH);
//            } else {
//                System.err.println("Failed to create directory at: " + QR_CODE_PATH);
//                return; // Exit if directory creation fails
//            }
//        }
//
//        // Path to save the generated QR code
//        String filePath = QR_CODE_PATH + tableNumber + "-qr.png";
//        generateQRCodeImage(qrContent, 200, 200, filePath);
//    }
//
//    private void generateQRCodeImage(String data, int width, int height, String filePath) throws WriterException, IOException {
//        // Use ZXing library to generate QR code
//        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
//
//        // Convert BitMatrix to BufferedImage
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        image.createGraphics();
//
//        Graphics2D graphics = (Graphics2D) image.getGraphics();
//        graphics.setColor(Color.WHITE);
//        graphics.fillRect(0, 0, width, height);
//        graphics.setColor(Color.BLACK);
//
//        // Draw the QR code on the image
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if (bitMatrix.get(i, j)) {
//                    image.setRGB(i, j, Color.BLACK.getRGB());
//                }
//            }
//        }
//
//        // Write the image to a file
//        File qrFile = new File(filePath);
//        ImageIO.write(image, "PNG", qrFile);
//
//        System.out.println("QR code generated and saved at: " + filePath);
//    }
//}
package com.demo.servicelmpl;

import com.demo.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private static final String QR_CODE_PATH = "./qrcodes/";

    @Override
    public void generateQRCode(String tableNumber, String seat, String password, Integer shopId) throws Exception {
        // Combine the table data and shopId to create a unique QR code content
        String qrContent = "http://localhost:5173/auto-login?tableNumber=" + tableNumber + "&seat=" + seat + "&password=" + password + "&shopId=" + shopId;

        // Ensure the directory exists
        File directory = new File(QR_CODE_PATH);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created at: " + QR_CODE_PATH);
            } else {
                System.err.println("Failed to create directory at: " + QR_CODE_PATH);
                return; // Exit if directory creation fails
            }
        }

        // Generate a unique filename using both tableNumber and shopId to avoid overwriting
        String filePath = QR_CODE_PATH + "QR_" + tableNumber + "_Shop" + shopId + ".png"; // Example: QR_T1_Shop1.png

        // Generate and save the QR code
        generateQRCodeImage(qrContent, 200, 200, filePath);
    }

    private void generateQRCodeImage(String data, int width, int height, String filePath) throws WriterException, IOException {
        // Use ZXing library to generate QR code
        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        // Convert BitMatrix to BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        // Draw the QR code on the image
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }

        // Write the image to a file
        File qrFile = new File(filePath);
        ImageIO.write(image, "PNG", qrFile);

        System.out.println("QR code generated and saved at: " + filePath);
    }
}
