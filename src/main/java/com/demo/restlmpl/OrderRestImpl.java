package com.demo.restlmpl;

import com.demo.POJO.Delivery;
import com.demo.POJO.Order;
import com.demo.constents.ResConstants;
import com.demo.dto.OrderRequest;
import com.demo.rest.OrderRest;
import com.demo.service.OrderService;
import com.demo.utils.ResUtils;
import com.demo.wrapper.OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
//@CrossOrigin(origins = "*") // Allow all origins
public class OrderRestImpl implements OrderRest {

    @Autowired
    private OrderService orderService;
    @Override
    public ResponseEntity<List<OrderWrapper>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

//    @PostMapping("/placeOrder")
//    public ResponseEntity<String> placeOrder(@RequestBody Map<String, Object> requestMap) {
//        try {
//            return orderService.placeOrder(requestMap);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@Override
public ResponseEntity<String> placeOrder(OrderRequest orderRequest) {
    return orderService.placeOrder(orderRequest);
}
    @Override
    public ResponseEntity<List<Order>> getOrderByShop(@PathVariable Integer shopId) {
        try {
            return new ResponseEntity<>(orderService.getOrderByShop(shopId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}