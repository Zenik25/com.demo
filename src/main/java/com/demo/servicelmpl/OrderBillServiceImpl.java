package com.demo.servicelmpl;

import com.demo.POJO.OrderBill;
import com.demo.dao.OrderBillDao;
import com.demo.dao.ShopDao;
import com.demo.service.OrderBillEmailService;
import com.demo.service.OrderBillService;
import com.demo.utils.EmailUtils;
import com.demo.wrapper.OrderBillWrapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

//import com.itextpdf.text.*;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.pdf.*;

@Service
public class OrderBillServiceImpl implements OrderBillService {

    @Autowired
    private OrderBillDao orderBillDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private OrderBillEmailService orderBillEmailService;


    @Override
    public ResponseEntity<String> generateAndSendBill(OrderBillWrapper wrapper) {
        try {
            OrderBill bill = new OrderBill();
            bill.setTableNumber(wrapper.getTableNumber());
            bill.setOrderedProductNames(wrapper.getOrderedProductNames());
            bill.setQuantity(wrapper.getQuantity());
            bill.setPrice(wrapper.getPrice());
            bill.setTotalPrice(wrapper.getTotalPrice());
            bill.setTaxAmount(wrapper.getTaxAmount());
            bill.setNetTotal(wrapper.getNetTotal());
            bill.setEmail(wrapper.getEmail());
            bill.setShop(shopDao.findById(wrapper.getShopId()).orElse(null));

            orderBillDao.save(bill); // Hibernate inserts here

            if (bill.getEmail() == null || bill.getEmail().isEmpty()) {
                System.err.println("❌ Email is null or empty. Skipping email send.");
                return new ResponseEntity<>("❌ Email not provided.", HttpStatus.BAD_REQUEST);
            }

            System.out.println("✅ Saved OrderBill for: " + bill.getEmail());

            // Build email content
            StringBuilder content = new StringBuilder();
            content.append("🧾 Order Bill 🧾\n")
                    .append("Table Number: ").append(bill.getTableNumber()).append("\n")
                    .append("Order Date: ").append(bill.getOrderDate()).append(" ").append(bill.getOrderTime()).append("\n\n")
                    .append("Items:\n");

            List<String> items = bill.getOrderedProductNames();
            List<Integer> quantities = bill.getQuantity();
            List<Integer> prices = bill.getPrice();

            for (int i = 0; i < items.size(); i++) {
                content.append(items.get(i)).append(" x ").append(quantities.get(i))
                        .append(" = ").append(prices.get(i) * quantities.get(i)).append(" Ks\n");
            }

            content.append("\nTax (5%): ").append(bill.getTaxAmount()).append(" Ks\n")
                    .append("Total: ").append(bill.getNetTotal()).append(" Ks\n");

            // DEBUG: Log before sending email
            System.out.println("📧 Sending email to: " + bill.getEmail());

            // Use your new service
            orderBillEmailService.sendOrderBillEmail(bill);

            System.out.println("✅ Email sent successfully");

            return new ResponseEntity<>("✅ Bill Generated and Email Sent", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("❌ Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
