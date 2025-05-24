//package com.demo.restlmpl;
//
//import com.demo.rest.DeliveryRest;
//import com.demo.POJO.Delivery;
//import com.demo.service.DeliveryService;
//import com.demo.wrapper.DeliveryWrapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:5173") // Allow React frontend
//public class DeliveryRestImpl implements DeliveryRest {
//    private final DeliveryService deliveryService;
//
//    public DeliveryRestImpl(DeliveryService deliveryService) {
//        this.deliveryService = deliveryService;
//    }
//
//    @Override
//    public ResponseEntity<Delivery> placeOrder(@RequestBody Delivery delivery) {
//        return ResponseEntity.ok(deliveryService.placeOrder(delivery));
//    }
//
//    @Override
//    public ResponseEntity<List<Delivery>> getAllOrders() {
//        return ResponseEntity.ok(deliveryService.getAllOrders());
//    }
//
//    @Override
//    public ResponseEntity<List<DeliveryWrapper>> getOrdersByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(deliveryService.getOrdersByEmail(email));
//    }
//}


package com.demo.restlmpl;

import com.demo.POJO.Delivery;
import com.demo.POJO.OrderedProduct;
import com.demo.POJO.Shop;
import com.demo.dao.DeliveryDao;
import com.demo.dto.PlaceOrderRequest;
import com.demo.dto.ShopDeliveryResponse;
import com.demo.rest.DeliveryRest;
import com.demo.service.DeliveryService;
import com.demo.service.ShopService;
import com.demo.servicelmpl.DeliveryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
//@CrossOrigin(origins = "*") // Allow all origins

public class DeliveryRestImpl implements DeliveryRest {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryServiceImpl deliveryServicelmpl;


//    public ResponseEntity<String> placeOrder(@RequestBody Delivery delivery) {
//        return deliveryService.placeOrder(delivery);
//    }
//    private final DeliveryService deliveryService;

    public DeliveryRestImpl(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

//    @PostMapping("/placeOrder")
////    public ResponseEntity<String> placeOrder(@RequestBody Delivery delivery) {
////        for (OrderedProduct product : delivery.getOrderedProducts()) {
////            product.setDelivery(delivery);  // Ensure foreign key is set
////        }
////        deliveryService.save(delivery);
////        return ResponseEntity.ok("Order placed successfully");
////    }
//
//    public ResponseEntity<String> placeOrder(DeliveryRequest request) {
//        String response = deliveryService.placeOrder(request.getDelivery(), request.getPaymentMethod());
//        return ResponseEntity.ok(response);
//}
@Override
@PostMapping("/placeOrder")
public Delivery placeOrder(@RequestBody PlaceOrderRequest request) {
    return deliveryService.placeOrder(request); // Delegate to service


}

//    @Autowired
//    private DeliveryService deliveryService;

    @Autowired
    private ShopService shopService;

    @Override
    public ResponseEntity<ShopDeliveryResponse> getOrderByShop(@PathVariable Integer shopId) {
        try {
            List<Delivery> deliveries = deliveryService.getOrderByShop(shopId);
            Shop shop = shopService.findById(shopId);

            if (shop == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            ShopDeliveryResponse response = new ShopDeliveryResponse(shop.getName(), deliveries);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @Override
    public ResponseEntity<List<Delivery>> getAllOrders() {
        return deliveryService.getAllOrders();
    }

    @Override
    public ResponseEntity<Delivery> getOrderById(@PathVariable Long id) {
        return deliveryService.getOrderById(id);
    }


    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status) {
        return deliveryService.updateDeliveryStatus(id, status);
    }

//        @GetMapping("/getByEmail")
//    public List<Delivery> getOrdersByEmail(@RequestParam String email) {
//        return deliveryService.getOrdersByEmail(email);
//    }
    @Autowired
    DeliveryDao deliveryDao;
@GetMapping("/getByEmail")
public ResponseEntity<List<Delivery>> getOrdersByEmail(@RequestParam String email) {
    System.out.println("Fetching orders for email: " + email);
    List<Delivery> orders = deliveryService.getOrdersByEmail(email);
    return ResponseEntity.ok(orders);
}
    @GetMapping("/getByShop/{shopId}")
    public List<Delivery> getDeliveriesByShop(@PathVariable Integer shopId) {
        return deliveryDao.findByShopId(shopId);
    }

}
