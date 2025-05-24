package com.demo.service;

import com.demo.POJO.Bill;
import com.demo.POJO.Delivery;
import com.demo.POJO.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {
    Email saveEmail(Email email);
    void sendEmail(Email email);
    void sendBillEmail(Delivery delivery);
//    @Autowired
//    private JavaMailSender mailSender;

//    public void sendBillEmail(Bill bill) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(bill.getEmail());
//        message.setSubject("Your Order Invoice - " + bill.getUuid());
//
//        String emailBody = "Dear " + bill.getName() + ",\n\n"
//                + "Thank you for your order. Below are the details:\n\n"
//                + "Order ID: " + bill.getUuid() + "\n"
//                + "Total Amount: " + bill.getTotal() + " MMK\n"
//                + "Payment Method: " + bill.getPaymentMethod() + "\n"
//                + "Products: " + bill.getProductDetails() + "\n\n"
//                + "We appreciate your business!\n\n"
//                + "Best regards,\nYour Food Delivery Team";
//
//        message.setText(emailBody);
//        mailSender.send(message);
//    }// Add sendEmail method
}
