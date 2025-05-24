//package com.demo.servicelmpl;
//
//import com.demo.JWT.JwtFilter;
//import com.demo.POJO.Bill;
//import com.demo.constents.ResConstants;
//import com.demo.dao.BillDao;
//import com.demo.service.BillService;
//import com.demo.utils.ResUtils;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.pdfbox.io.IOUtils;
//import org.json.JSONArray;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//
//@Slf4j
//@Service
//public class BillServiceImpl implements BillService {
//
//    @Autowired
//    JwtFilter jwtFilter;
//
//    @Autowired
//    BillDao billDao;
//
//    @Override
//    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
//        log.info("Inside generateReport");
//        try {
//            String fileName;
//            if(validateRequestMap(requestMap)) {
//                if (requestMap.containsKey("isGenerate") && !(Boolean)requestMap.get("isGenerate")){
//                    fileName = (String) requestMap.get("uuid");
//                }else {
//                    fileName = ResUtils.getUUID();
//                    requestMap.put("uuid",fileName);
//                    insertBill(requestMap);
//                }
//
//                String data = "Name: "+requestMap.get("name") +"\n"+"Contact Number: "+requestMap.get("contactNumber")+
//                        "\n"+"Email: "+requestMap.get("email")+"\n"+"Payment Method: "+requestMap.get("paymentMethod");
//
//                Document document = new Document();
//                PdfWriter.getInstance(document,new FileOutputStream(ResConstants.STORE_LOCATION+"\\"+fileName+".pdf"));
//                document.open();
//                setRectangleInPdf(document);
//
//                Paragraph chunk = new Paragraph("Restaurant Management System", getFont("Header"));
//                chunk.setAlignment(Element.ALIGN_CENTER);
//                document.add(chunk);
//
//                Paragraph paragraph = new Paragraph(data+"\n \n",getFont("Data"));
//                document.add(paragraph);
//
//                PdfPTable table = new PdfPTable(5);
//                table.setWidthPercentage(100);
//                addTableHeader(table);
//
//                JSONArray jsonArray = ResUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
//                for(int i=0;i<jsonArray.length();i++){
//                    addRows(table,ResUtils.getMapFromJson(jsonArray.getString(i)));
//                }
//
//                document.add(table);
//
//                Paragraph footer = new Paragraph("Total : "+requestMap.get("totalAmount")+"\n"
//                +"Thank you for visiting.Please visit again!",getFont("Date"));
//                document.add(footer);
//                document.close();
//                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);
//
//            }
//            return ResUtils.getResponseEntity("Required data not found.", HttpStatus.BAD_REQUEST);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//
//    private void addRows(PdfPTable table, Map<String, Object> data) {
//        log.info("Inside addRows");
//        table.addCell((String) data.get("name"));
//        table.addCell((String) data.get("category"));
//        table.addCell((String) data.get("quantity"));
//        table.addCell(Double.toString((Double)data.get("price")));
//        table.addCell(Double.toString((Double)data.get("total")));
//    }
//
//    private void addTableHeader(PdfPTable table) {
//        log.info("Inside addTableHeader");
//        Stream.of("Name","Category","Quantity","Price","Sub Total")
//                .forEach(columnTitle -> {
//                    PdfPCell header = new PdfPCell();
//                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                    header.setBorderWidth(2);
//                    header.setPhrase(new Phrase(columnTitle));
//                    header.setBackgroundColor(BaseColor.YELLOW);
//                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    header.setVerticalAlignment(Element.ALIGN_CENTER);
//                    table.addCell(header);
//                });
//    }
//
//    private Font getFont(String type) {
//        log.info("Inside getFont");
//        switch (type) {
//            case "Header":
//                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
//                headerFont.setStyle(Font.BOLD);
//                return headerFont;
//            case "Data":
//                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
//                dataFont.setStyle(Font.BOLD);
//                return dataFont;
//            default:
//                return new Font();
//        }
//    }
//
//    private void setRectangleInPdf(Document document) throws DocumentException {
//        log.info("Inside setRectangleInPdf");
//        Rectangle rect= new Rectangle(577,825,18,15);
//        rect.enableBorderSide(1);
//        rect.enableBorderSide(2);
//        rect.enableBorderSide(4);
//        rect.enableBorderSide(8);
//        rect.setBorderColor(BaseColor.BLACK);
//        rect.setBorderWidth(1);
//        document.add(rect);
//    }

//    private void insertBill(Map<String, Object> requestMap) {
//        try {
//            Bill bill=new Bill();
//            bill.setUuid((String) requestMap.get("uuid"));
//            bill.setName((String) requestMap.get("name"));
//            bill.setEmail((String) requestMap.get("email"));
//            bill.setContactNumber((String) requestMap.get("contactNumber"));
//            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
//            bill.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
//            bill.setProductDetail((String) requestMap.get("productDetails"));
//            bill.setCreatedBy(jwtFilter.getCurrentUser());
//            billDao.save(bill);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    private boolean validateRequestMap(Map<String, Object> requestMap) {
//        return requestMap.containsKey("name") &&
//                requestMap.containsKey("contactNumber") &&
//                requestMap.containsKey("email") &&
//                requestMap.containsKey("paymentMethod") &&
//                requestMap.containsKey("productDetails") &&
//                requestMap.containsKey("totalAmount");
//    }
//    @Override
//    public ResponseEntity<List<Bill>> getBills() {
//        List<Bill> list =new ArrayList<>();
//        if (jwtFilter.isAdmin()){
//            list = billDao.getAllBills();
//        }else {
//            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
//        }
//        return new ResponseEntity<>(list,HttpStatus.OK);
//
//    }
//
//    @Override
//    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
//        log.info("Inside getPdf : requestMap {}", requestMap);
//        try {
//            byte[] byteArray = new byte[0];
//            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
//                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
//
//            String filePath = ResConstants.STORE_LOCATION+"\\"+(String) requestMap.get("uuid")+".pdf";
//            if (ResUtils.isFileExist(filePath)) {
//                byteArray = getByteArray(filePath);
//                return new ResponseEntity<>(byteArray, HttpStatus.OK);
//            }
//            else {
//                requestMap.put("isGenerate",false);
//                generateReport(requestMap);
//                byteArray = getByteArray(filePath);
//                return new ResponseEntity<>(byteArray, HttpStatus.OK);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//
//
//    private byte[] getByteArray(String filePath) throws Exception {
//        File initialFile = new File(filePath);
//        InputStream targetStream = new FileInputStream(initialFile);
//        byte[] byteArray = IOUtils.toByteArray(targetStream);
//        targetStream.close();
//        return byteArray;
//    }
//    @Override
//    public ResponseEntity<String> deleteBill(Integer id) {
//        try{
//            Optional optional = billDao.findById(id);
//            if (!optional.isEmpty()){
//                billDao.deleteById(id);
//                return ResUtils.getResponseEntity("Bill Deleted Successfully",HttpStatus.OK);
//            }
//            return ResUtils.getResponseEntity("Bill Id doesn't not exit",HttpStatus.OK);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}

//package com.demo.servicelmpl;
//
//import com.demo.dao.BillDao;
//import com.demo.POJO.Bill;
//import com.demo.service.BillService;
//import com.demo.service.EmailService;
//import com.demo.wrapper.BillWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class BillServiceImpl implements BillService {
//
//    @Autowired
//    private BillDao billDao;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Override
//    public Bill generateBill(Map<String, Object> orderDetails) {
//        Bill bill = new Bill();
//        bill.setUuid(UUID.randomUUID().toString());
//        bill.setName(orderDetails.get("name").toString());
//        bill.setEmail(orderDetails.get("email").toString());
//        bill.setContactNumber(orderDetails.get("contactNumber").toString());
//        bill.setPaymentMethod(orderDetails.get("paymentMethod").toString());
//        bill.setTotal(Integer.parseInt(orderDetails.get("total").toString()));
//        bill.setProductDetails(orderDetails.get("productDetails").toString());
//        bill.setCreatedBy(orderDetails.get("createdBy").toString());
//
//        Bill savedBill = billDao.save(bill);
//
//        // Send bill via email
//        emailService.sendBillEmail(savedBill);
//
//        return savedBill;
//    }
//
//    @Override
//    public List<Bill> getBillsByUserEmail(String email) {
//        return billDao.getBillByUserEmail(email);
//    }
//}
package com.demo.servicelmpl;

import com.demo.JWT.JwtFilter;
import com.demo.POJO.*;
import com.demo.constents.ResConstants;
import com.demo.dao.BillDao;
import com.demo.dao.DeliveryDao;
import com.demo.dao.ShopDao;
import com.demo.service.BillService;
import com.demo.service.EmailService;
import com.demo.service.EmailSenderService;
import com.demo.utils.ResUtils;
import com.itextpdf.text.pdf.PdfContentByte;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import javax.swing.text.Document;
import java.nio.file.Files;
import com.itextpdf.text.Font;

import java.time.format.DateTimeFormatter;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private BillDao billDao;

    Delivery delivery= new Delivery();


    @Autowired
    private EmailSenderService emailSenderService;

    private static final String PDF_LOCATION = "bills/";

//    @Override
//    public ResponseEntity<String> generateBillFromDelivery(Long deliveryId) {
//        Optional<Delivery> deliveryOpt = deliveryDao.findById(deliveryId);
//        if (!deliveryOpt.isPresent()) {
//            return new ResponseEntity<>("Delivery not found", HttpStatus.NOT_FOUND);
//        }
//
//        Delivery delivery = deliveryOpt.get();
//        String uuid = UUID.randomUUID().toString();
//        String fileName = uuid + ".pdf";
//        String filePath = PDF_LOCATION + fileName;
//
//        String productDetails = delivery.getOrderedProducts().stream()
//                .map(p -> p.getProductName() + " x" + p.getQuantity())
//                .collect(Collectors.joining(", "));
//
//        // Save Bill
//        Bill bill = new Bill();
//        bill.setUuid(uuid);
//        bill.setName(delivery.getName());
//        bill.setEmail(delivery.getEmail());
//        bill.setPhone(delivery.getPhone());
//        bill.setPaymentMethod(delivery.getPaymentMethod());
//        bill.setTotalAmount(delivery.getTotalAmount());
//        bill.setProductDetails(productDetails);
//        bill.setCreatedDate(delivery.getOrderDate());
//        bill.setOrderTime(delivery.getOrderTime());
//        billDao.save(bill);
//
//        try {
//            billService.generatePdf(filePath, bill);
//            return new ResponseEntity<>("Bill generated with UUID: " + uuid, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("PDF generation failed", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@Override
public ResponseEntity<String> generateBillFromDelivery(Long deliveryId) {
    Optional<Delivery> deliveryOpt = deliveryDao.findById(deliveryId);
    if (!deliveryOpt.isPresent()) {
        return new ResponseEntity<>("Delivery not found", HttpStatus.NOT_FOUND);
    }

    Delivery delivery = deliveryOpt.get();
    String uuid = UUID.randomUUID().toString();
    String fileName = uuid + ".pdf";  // Generate a unique file name for the PDF
    String filePath = PDF_LOCATION + fileName;  // Define the location where the PDF will be saved

    String productDetails = delivery.getOrderedProducts().stream()
            .map(p -> p.getProductName() + " x" + p.getQuantity())
            .collect(Collectors.joining(", "));  // Concatenate ordered products into a string

    // Create and save the Bill object
    Bill bill = new Bill();
    bill.setUuid(uuid);
    bill.setName(delivery.getName());
    bill.setEmail(delivery.getEmail());
    bill.setPhone(delivery.getPhone());
    bill.setPaymentMethod(delivery.getPaymentMethod());
    bill.setTotalAmount(delivery.getTotalAmount());
    bill.setProductDetails(productDetails);
    bill.setCreatedDate(delivery.getOrderDate());
    bill.setOrderTime(delivery.getOrderTime());
    billDao.save(bill);  // Save the Bill to the database

    try {
        // Generate the PDF and save it to the filePath
        this.generatePdf(filePath, bill);  // Ensure this method exists and works as expected

        return new ResponseEntity<>("Bill generated with UUID: " + uuid, HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();  // Log the exception for debugging
        return new ResponseEntity<>("PDF generation failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    //    private void generatePdf(String filePath, Bill bill) throws Exception {
//        Document document = new Document();
//        File file = new File(PDF_LOCATION);
//        if (!file.exists()) file.mkdirs();
//
//        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//        document.open();
//
//        Rectangle rect = new Rectangle(577, 825, 18, 15);
//        rect.enableBorderSide(1);
//        rect.enableBorderSide(2);
//        rect.enableBorderSide(4);
//        rect.enableBorderSide(8);
//        rect.setBorderColor(BaseColor.BLACK);
//        rect.setBorderWidth(1);
//        document.add(rect);
//
//        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
//        Paragraph header = new Paragraph("Food Delivery Invoice", headerFont);
//        header.setAlignment(Element.ALIGN_CENTER);
//        document.add(header);
//
//        Paragraph body = new Paragraph(
//                "Name: " + bill.getName() + "\n" +
//                        "Email: " + bill.getEmail() + "\n" +
//                        "Phone: " + bill.getPhone() + "\n" +
//                        "Payment Method: " + bill.getPaymentMethod() + "\n" +
//                        "Date: " + bill.getOrderDate() + " " + bill.getOrderTime() + "\n\n" +
//                        "Products: " + bill.getProductDetails() + "\n" +
//                        "Total: " + bill.getTotalAmount() + " MMK\n",
//                FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)
//        );
//        document.add(body);
//        Font italicFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
//        Paragraph footer = new Paragraph("Thank you for your order!", italicFont);
//        footer.setAlignment(Element.ALIGN_CENTER);
//        document.add(footer);
//
//        document.close();
//        emailSenderService.sendEmailWithAttachment(
//                delivery.getEmail(),
//                "Your Invoice from Food Delivery",
//                "Dear " + delivery.getName() + ",\n\nPlease find your invoice attached.",
//                new File(filePath)
//        );
//
//    }

    private void setRectangleInPdf(Document document, PdfWriter writer) {
        log.info("Inside setRectangleInPdf");
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBorderColor(BaseColor.BLACK);
        canvas.rectangle(rect);
    }


    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type) {
            case "Header":
                return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD, BaseColor.BLACK);
            case "Data":
                return FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
            default:
                return FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
        }
    }
@Autowired
   private ShopDao shopDao;

    @Override
    public void generatePdf(String filePath, Bill bill) throws Exception {
        try {
            // Ensure file path uses proper platform-specific separator
            String fileName = ResConstants.STORE_LOCATION + File.separator + bill.getDelivery().getId() + ".pdf";
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            setRectangleInPdf(document, writer);

            // Fonts
            Font headerFont = getFont("Header");
            Font dataFont = getFont("Data");

            Delivery delivery = bill.getDelivery();
            if (delivery == null) {
                throw new IllegalArgumentException("Delivery is not set in Bill");
            }

            String shopName = "Unknown Shop";
            Shop shop = delivery.getShop();
            if (shop != null) {
                shopName = shop.getName();
            }


//             Order ID, Customer Info, and Order Details
            Paragraph invoice = new Paragraph("Bill Details", FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18));
            invoice.setAlignment(Element.ALIGN_CENTER);
            document.add(invoice);

//            Paragraph shopHeader = new Paragraph("Shop: " + bill.getShopName(), FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 16));
//            shopHeader.setAlignment(Element.ALIGN_CENTER);
//            document.add(shopHeader);
//            document.add(new Paragraph(" ")); // Add space after shop name

//            document.add(new Paragraph("Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18)));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("From: " + shopName, dataFont)); // ✅ Add Shop Name
            document.add(new Paragraph("Order ID: " + delivery.getId(), dataFont));
            document.add(new Paragraph("Customer: " + delivery.getName(), dataFont));
            document.add(new Paragraph("Email: " + delivery.getEmail(), dataFont));
            document.add(new Paragraph("Phone: " + delivery.getPhone(), dataFont));
            document.add(new Paragraph("Address: " + delivery.getStreet(), dataFont));
            document.add(new Paragraph("Order Date: " + delivery.getOrderDate(), dataFont));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String formattedTime = delivery.getOrderTime().format(formatter);

            document.add(new Paragraph("Order Time: " + formattedTime, dataFont));

//            document.add(new Paragraph("Order Time: " + delivery.getOrderTime(), dataFont));
            document.add(new Paragraph(" "));


            PdfPTable table = new PdfPTable(4); // 4 columns: Product Name, Quantity, Price, Total
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
//            table.setSpacingAfter(10f);

// Table Headers
            Stream.of("Product", "Qty", "Price", "Sub_Total").forEach(header -> {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(headerCell);
            });

// Table Rows
            for (OrderedProduct product : delivery.getOrderedProducts()) {
                Integer price = product.getPrice(); // <-- Your original line that failed
                if (price == null) {
                    price = 0; // Default to 0 if null
                }
                int totalPrice = price * product.getQuantity();

                table.addCell(new PdfPCell(new Phrase(product.getProductName(), dataFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(product.getQuantity()), dataFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(price), dataFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(totalPrice), dataFont)));
            }

            document.add(table); // Add the complete table to the PDF


            // Total amount and payment method
            document.add(new Paragraph(" "));
            int tax = delivery.getTax();
            document.add(new Paragraph("Taxes (5%): " + tax + " MMK", dataFont));
            document.add(new Paragraph("DeliveryFees: " + delivery.getDeliveryFee() + " MMK", dataFont));


//            int netTotal = bill.getTotalAmount() + tax;
            document.add(new Paragraph("Net Total: " + delivery.getTotalAmount() + " MMK", dataFont));
            document.add(new Paragraph("Payment Method: " + bill.getPaymentMethod(), dataFont));
            document.add(new Paragraph(" "));
            Paragraph thankYou = new Paragraph("Thank you for visiting. Please visit again!", FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 15));
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);



            // Close the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }



    @Override
    public ResponseEntity<byte[]> getBillPdf(String uuid) {
        try {
            String path = PDF_LOCATION + uuid + ".pdf";
            File file = new File(path);
            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            byte[] bytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + uuid + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

