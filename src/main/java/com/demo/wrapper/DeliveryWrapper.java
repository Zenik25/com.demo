////package com.demo.wrapper;
////
////import lombok.AllArgsConstructor;
////import lombok.Data;
////
////@Data
////@AllArgsConstructor
////public class DeliveryWrapper {
////    private String firstName;
////    private String lastName;
////    private String email;
////    private String street;
////    private String phone;
////    private Double totalAmount;
////}
//
//
//
//package com.demo.wrapper;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class DeliveryWrapper {
//
//    private String name;
//    private String email;
//    private String street;
//    private String phone;
//    private Double totalAmount;
//}
//package com.demo.wrapper;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class DeliveryWrapper {
//
//    private Long id;              // Assuming you have an 'id' field in the Delivery table
//    private String name;          // Name field from Delivery entity
//    private String email;         // Email field from Delivery entity
//    private String street;        // Street field from Delivery entity
//    private String phone;         // Phone field from Delivery entity
//    private String date;          // Date field from Delivery entity
//    private String time;          // Time field from Delivery entity
//    private Double totalAmount;   // Total Amount field from Delivery entity
//    private String itemType;      // Item Type field from Delivery entity
//    private String paymentMethod; // Payment Method field from Delivery entity
//    private String image;         // Image field from Delivery entity (if exists)
//}
package com.demo.wrapper;

import com.demo.POJO.OrderedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DeliveryWrapper {

    private Long id;              // Assuming the 'id' field exists in the Delivery entity
    private String name;          // Name from Delivery entity
    private String email;         // Email from Delivery entity
    private String street;        // Street from Delivery entity
    private String phone;         // Phone from Delivery entity
    private String status;         // Phone from Delivery entity
    private int totalAmount;
    private Integer deliveryFee;
    private  Integer tax;// Total Amount from Delivery entity (int)
    private String paymentMethod; // Payment Method (e.g., Cash, KBZ Pay)
    private LocalDate orderDate;  // Order Date from Delivery entity
    private LocalTime orderTime;  // Order Time from Delivery entity
    private List<OrderedProduct> orderedProducts;
//    private Integer shopId;
//    private String shopName;// List of ordered products (related entity)
}

