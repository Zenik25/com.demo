



package com.demo.servicelmpl;

import com.demo.POJO.*;
import com.demo.dao.BillDao;
import com.demo.dao.DeliveryDao;
import com.demo.dao.ProductDao;
import com.demo.dao.ShopDao;
import com.demo.dto.PlaceOrderRequest;
import com.demo.service.BillService;
import com.demo.service.DeliveryService;
import com.demo.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.demo.constents.ResConstants;
@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private BillService billService;

@Autowired
    ProductDao productDao;


    @Autowired
    private BillDao billDao; // DAO for Bill

    @Autowired
    private EmailService emailService;

//@Override
//public Delivery placeOrder(PlaceOrderRequest request) {
//    Delivery delivery = request.getDelivery();
//
//    // Ensure Name is being set
//    if (delivery.getName() == null || delivery.getName().isEmpty()) {
//        delivery.setName("Guest");
//    }
//
//    // Set payment method
//    if (request.getPaymentMethod() != null) {
//        delivery.setPaymentMethod(request.getPaymentMethod());
//    }
//
//    // Save the Delivery entity with the nested OrderedProducts
//    for (OrderedProduct orderedProduct : delivery.getOrderedProducts()) {
//        orderedProduct.setDelivery(delivery); // Set the back-reference to the delivery
//    }
//
//    // Save the delivery and its ordered products
//    Delivery savedDelivery = deliveryDao.save(delivery);
////
////    // After saving the delivery, trigger the email sending
////    emailService.sendBillEmail(savedDelivery); // Call the email service
////
////    return savedDelivery;
//    // Generate Bill
//    Bill bill = new Bill();
//    bill.setUuid(UUID.randomUUID().toString());
//    bill.setTotalAmount(delivery.getTotalAmount());
//    bill.setPaymentMethod(delivery.getPaymentMethod());
//    bill.setCreatedDate(LocalDate.now());
//    bill.setDelivery(savedDelivery);
//    billDao.save(bill);  // or billService.save(bill)
//
//    String filePath = "path/to/your/storage/location/" + bill.getUuid() + ".pdf";  // Specify where you want to save the PDF file
//
//    try {
//        // Generate the PDF with bill information and save to the specified filePath
//        billService.generatePdf(filePath, bill);  // Call to BillService to generate the PDF
//
//        // Send email with the PDF attachment
//        emailService.sendBillEmail(savedDelivery);  // Send email with PDF attachment
//
//        return savedDelivery;  // Return the savedDelivery with OK status
//    } catch (Exception e) {
//        e.printStackTrace();
//
//        // Return null or handle it accordingly if you want to notify the frontend about failure
//        return null; // Return error status if something fails
//    }
//
//}\
@Autowired
private ShopDao shopDao;

//    @Autowired
//    private DeliveryDao deliveryDao;


    @Override
    public List<Delivery> getOrderByShop(Integer shopId) {
        return deliveryDao.findByShopId(shopId);
    }


    @Override
    @Transactional
    public Delivery placeOrder(PlaceOrderRequest request) {
        Delivery delivery = request.getDelivery();

        // Set default name if missing
        if (delivery.getName() == null || delivery.getName().isEmpty()) {
            delivery.setName("Guest");
        }

        // Set payment method
        if (request.getPaymentMethod() != null) {
            delivery.setPaymentMethod(request.getPaymentMethod());
        }

        // Validate shopId and get Shop
        // NEW
        if (request.getShopId() == null) {
            throw new IllegalArgumentException("Shop ID is required.");
        }

        Shop shop = shopDao.findById(request.getShopId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid shop ID"));

        delivery.setShop(shop);
// ✅ Set the Shop relationship (don't use delivery.setShopId)

        // Link ordered products to delivery and assign prices
        for (OrderedProduct orderedProduct : delivery.getOrderedProducts()) {
            orderedProduct.setDelivery(delivery);

            if (orderedProduct.getPrice() == null || orderedProduct.getPrice() == 0) {
                Product product = productDao.findById(orderedProduct.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + orderedProduct.getProductId()));

                orderedProduct.setPrice(product.getPrice());
            }
        }

        // Save delivery and ordered products
        Delivery savedDelivery = deliveryDao.save(delivery);

        // Build product details for bill
        String productDetails = savedDelivery.getOrderedProducts().stream()
                .map(p -> p.getProductName() + " x" + p.getQuantity())
                .collect(Collectors.joining(", "));

        // Create and save Bill
        Bill bill = new Bill();
        bill.setUuid(UUID.randomUUID().toString());
        bill.setName(savedDelivery.getName());
        bill.setEmail(savedDelivery.getEmail());
        bill.setPhone(savedDelivery.getPhone());
        bill.setPaymentMethod(savedDelivery.getPaymentMethod());
        bill.setTotalAmount(savedDelivery.getTotalAmount());
        bill.setProductDetails(productDetails);
        bill.setCreatedDate(savedDelivery.getOrderDate());
        bill.setOrderTime(savedDelivery.getOrderTime());
        bill.setDeliveryFee(savedDelivery.getDeliveryFee());
        bill.setTax(savedDelivery.getTax());
        bill.setDelivery(savedDelivery);
        bill.setShopName(shop.getName());

        billDao.save(bill);

        // PDF path (consider moving to config)
        String filePath = "D:\\Users\\Lin Htet Ko Lay\\Downloads\\vv" + bill.getUuid() + ".pdf";

        try {
            billService.generatePdf(filePath, bill);
            emailService.sendBillEmail(savedDelivery);
            return savedDelivery;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate PDF or send email");
        }
    }



    @Override
    public ResponseEntity<String> updateDeliveryStatus(Long id, String status) {
        Optional<Delivery> optionalDelivery = deliveryDao.findById(id);
        if (optionalDelivery.isPresent()) {
            Delivery delivery = optionalDelivery.get();
            if (!status.equalsIgnoreCase("Confirming") &&
                    !status.equalsIgnoreCase("Pending") &&
                    !status.equalsIgnoreCase("On Way") &&
                    !status.equalsIgnoreCase("Delivered")) {
                return new ResponseEntity<>("Invalid status", HttpStatus.BAD_REQUEST);
            }
            delivery.setStatus(status);
            deliveryDao.save(delivery);
            return new ResponseEntity<>("Delivery status updated to: " + status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delivery not found", HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<List<Delivery>> getAllOrders() {
        return ResponseEntity.ok(deliveryDao.findAll());
    }

    @Override
    public ResponseEntity<Delivery> getOrderById(Long id) {
        return deliveryDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Override
    public void save(Delivery delivery) {
        deliveryDao.save(delivery);
    }
    @Override
    public List<Delivery> getOrdersByEmail(String email) {
        return deliveryDao.findByEmail(email);
    }
}


