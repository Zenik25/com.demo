package com.demo.wrapper;

import com.demo.POJO.Bill;
import com.demo.POJO.Delivery;
import com.demo.dao.BillDao;

import java.util.UUID;

public class BillWrapper {

    private final BillDao billDao;

    public BillWrapper(BillDao billDao) {
        this.billDao = billDao;
    }

    // This method creates a new Bill from Delivery information
    public Bill createBillFromDelivery(Delivery delivery) {
        Bill bill = new Bill();
        bill.setUuid(UUID.randomUUID().toString()); // Generating a unique UUID
        bill.setName(delivery.getName());
        bill.setEmail(delivery.getEmail());
        bill.setPhone(delivery.getPhone());
        bill.setPaymentMethod(delivery.getPaymentMethod());
        bill.setTotalAmount(delivery.getTotalAmount());

        // Combine product details into a single string
        String productDetails = delivery.getOrderedProducts().stream()
                .map(p -> p.getProductName() + " x" + p.getQuantity())
                .reduce((prod1, prod2) -> prod1 + ", " + prod2)
                .orElse("No products");

        bill.setProductDetails(productDetails);
        bill.setCreatedDate(delivery.getOrderDate());
        bill.setOrderTime(delivery.getOrderTime());
        bill.setDeliveryFee(delivery.getDeliveryFee());
        bill.setTax(delivery.getTax());

        return bill;
    }

    // This method saves a Bill into the database
    public Bill saveBill(Bill bill) {
        return billDao.save(bill);
    }

    // Other utility methods can be added here if needed
}
