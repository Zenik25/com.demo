package com.demo.servicelmpl;
import com.demo.POJO.OrderBill;
import com.demo.constents.ResConstants;
import com.demo.dao.OrderBillDao;
import com.demo.dao.ShopDao;
import com.demo.service.OrderBillEmailService;
import com.demo.utils.PdfGeneratorUtil;
import com.demo.wrapper.OrderBillWrapper;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

//import javax.swing.text.Document;
import java.awt.*;
//import java.awt.Font;
//import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@Service
//public class OrderBillEmailServiceImpl implements OrderBillEmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Override
//    public void sendOrderBillEmail(OrderBill bill) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//
//            helper.setTo(bill.getEmail());
//            helper.setSubject("Your Order Invoice - Table " + bill.getTableNumber());
//
//            String body = "Dear Customer,\n\nPlease find attached your order invoice.\n\nThank you!";
//            helper.setText(body);
//
//            // PDF Generation Path
//            String filePath = ResConstants.STORE_LOCATION + File.separator + bill.getId() + ".pdf";
//
//            // Generate PDF
//            PdfGeneratorUtil.generateOrderBillPdf(bill, filePath);
//
//            // Attach PDF
//            FileSystemResource file = new FileSystemResource(new File(filePath));
//            helper.addAttachment("OrderBill-" + bill.getId() + ".pdf", file);
//
//            javaMailSender.send(mimeMessage);
//            System.out.println("✅ Order bill PDF emailed: " + bill.getEmail());
//
//        } catch (Exception e) {
//            System.err.println("❌ Email send failed: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//}
@Service
public class OrderBillEmailServiceImpl implements OrderBillEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderBillEmail(OrderBill bill) {
        try {
            String fileName = "OrderBill_" + bill.getId() + ".pdf";
            String filePath = ResConstants.STORE_LOCATION + File.separator + fileName;

            // ⬇️ Generate the PDF first
            generateOrderBillPdf(filePath, bill);

            // ⬇️ Prepare and send email with attachment
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(bill.getEmail());
            helper.setSubject("Your Order Bill");
            helper.setText("Dear Customer,\n\nPlease find attached your order bill.\n\nThanks for choosing us.");

            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(fileName, file);

            javaMailSender.send(mimeMessage);
            System.out.println("✅ Email with bill PDF sent to: " + bill.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to send bill email: " + e.getMessage());
        }
    }

    // ⬇️ Paste this method right below the sendOrderBillEmail() method
    private void generateOrderBillPdf(String filePath, OrderBill bill) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(2);
        document.add(rect);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 12);
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        Paragraph title = new Paragraph("Order Bill", headerFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        document.add(new Paragraph("Table Number: " + bill.getTableNumber(), dataFont));
        document.add(new Paragraph("Email: " + bill.getEmail(), dataFont));
        document.add(new Paragraph("Order Date: " + bill.getOrderDate(), dataFont));
        document.add(new Paragraph("Order Time: " + bill.getOrderTime().format(formatter), dataFont));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        Stream.of("Product", "Qty", "Price", "Sub_Total").forEach(col -> {
            PdfPCell header = new PdfPCell(new Phrase(col, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(header);
        });

        for (int i = 0; i < bill.getOrderedProductNames().size(); i++) {
            String product = bill.getOrderedProductNames().get(i);
            int qty = bill.getQuantity().get(i);
            int price = bill.getPrice().get(i);
            int subtotal = qty * price;

            table.addCell(new Phrase(product, dataFont));
            table.addCell(new Phrase(String.valueOf(qty), dataFont));
            table.addCell(new Phrase(String.valueOf(price), dataFont));
            table.addCell(new Phrase(String.valueOf(subtotal), dataFont));
        }

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tax (5%): " + bill.getTaxAmount() + " MMK", dataFont));
        document.add(new Paragraph("Total: " + bill.getNetTotal() + " MMK", dataFont));
        document.add(new Paragraph(" "));

        Paragraph thanks = new Paragraph("Thank you for your order!", headerFont);
        thanks.setAlignment(Element.ALIGN_CENTER);
        document.add(thanks);

        document.close();
    }

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private OrderBillDao orderBillDao;
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

            orderBillDao.save(bill); // Save the bill

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

            // Call email sending logic (PDF generation inside it)
            sendOrderBillEmail(bill);

            System.out.println("✅ Email sent successfully");

            return new ResponseEntity<>("✅ Bill Generated and Email Sent", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("❌ Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


