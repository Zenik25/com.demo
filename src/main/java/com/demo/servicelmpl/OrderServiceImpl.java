package com.demo.servicelmpl;

import com.demo.POJO.Delivery;
import com.demo.POJO.Order;
import com.demo.POJO.Shop;
import com.demo.constents.ResConstants;
import com.demo.dao.OrderDao;
import com.demo.dao.ShopDao;
import com.demo.dto.OrderRequest;
import com.demo.dto.PlaceOrderRequest;
import com.demo.service.OrderBillEmailService;
import com.demo.service.OrderService;
import com.demo.utils.ResUtils;
import com.demo.wrapper.OrderBillWrapper;
import com.demo.wrapper.OrderWrapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.demo.utils.ResUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderBillEmailService orderBillEmailService;

    @Override
    public List<OrderWrapper> getAllOrders() {
        List<Order> orders = orderDao.getAllOrders();
        return orders.stream()
                .map(o -> new OrderWrapper(o.getId(), o.getTableNumber(), o.getOrderedProductNames(), o.getQuantity(), o.getTotalPrice(), o.getOrderDate(), o.getOrderTime(), o.getStatus(), o.getPrice()))
                .collect(Collectors.toList());
    }
    @Override
    public ResponseEntity<String> updateOrderStatus(Long id, String status) {
        Optional<Order> optionalOrder = orderDao.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!status.equalsIgnoreCase("Confirming") &&
                    !status.equalsIgnoreCase("Pending") &&
                    !status.equalsIgnoreCase("Canceled") &&
                    !status.equalsIgnoreCase("Completed")) {
                return new ResponseEntity<>("Invalid status", HttpStatus.BAD_REQUEST);
            }
            order.setStatus(status);
            orderDao.save(order);
            return new ResponseEntity<>("Delivery status updated to: " + status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delivery not found", HttpStatus.NOT_FOUND);
        }
    }
    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ResponseEntity<String> placeOrder(OrderRequest request) {
        try {
            // 1. Validate required fields
            if (request.getOrderedProductNames() == null || request.getOrderedProductNames().isEmpty()) {
                return new ResponseEntity<>("Ordered products cannot be empty!", HttpStatus.BAD_REQUEST);
            }

            if (request.getQuantity() == null || request.getQuantity().isEmpty()) {
                return new ResponseEntity<>("Quantity cannot be empty!", HttpStatus.BAD_REQUEST);
            }

            if (request.getShopId() == null) {
                return new ResponseEntity<>("Shop ID is required!", HttpStatus.BAD_REQUEST);
            }

            // 2. Fetch shop
            Shop shop = shopDao.findById(request.getShopId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid shop ID"));

            // 3. Save Order
            Order order = new Order();
            order.setTableNumber(request.getTableNumber());
            order.setOrderedProductNames(request.getOrderedProductNames());
            order.setPrice(request.getPrice());
            order.setQuantity(request.getQuantity());
            order.setTotalPrice(request.getTotalPrice());
            order.setOrderType(request.getOrderType());
            order.setEmail(request.getEmail());
            order.setShop(shop);

            if (request.getStatus() != null) {
                order.setStatus(request.getStatus());
            }

            orderDao.save(order);

            // 4. Build OrderBillWrapper and call generateAndSendBill
            OrderBillWrapper wrapper = new OrderBillWrapper();
            wrapper.setTableNumber(request.getTableNumber());
            wrapper.setOrderedProductNames(request.getOrderedProductNames());
            wrapper.setQuantity(request.getQuantity());
            wrapper.setPrice(request.getPrice());
            wrapper.setTotalPrice(request.getTotalPrice());

            double tax = request.getTotalPrice() * 0.05;
            double netTotal = request.getTotalPrice() + tax;

            wrapper.setTaxAmount((int) tax);
            wrapper.setNetTotal((int) netTotal);
            wrapper.setEmail(request.getEmail());
            wrapper.setShopId(request.getShopId());

            orderBillEmailService.generateAndSendBill(wrapper);

            return ResponseEntity.ok("{\"message\": \"Order placed and email sent successfully!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public List<Order> getOrderByShop(Integer shopId) {
        return orderDao.findByShopId(shopId);
    }



}
