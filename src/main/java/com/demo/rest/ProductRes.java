package com.demo.rest;

import com.demo.POJO.Product;
import com.demo.dto.ShopProductsResponse;
import com.demo.dto.ShopTablesResponse;
import com.demo.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping(path = "/product")
public interface ProductRes {

//    @PostMapping(path = "/add", consumes = "multipart/form-data")
//    ResponseEntity<String> addNewProduct(
//            @RequestParam("name") String name,
//            @RequestParam("description") String description,
//            @RequestParam("price") Integer price,
//            @RequestParam("categoryId") Integer categoryId,
//            @RequestParam("image") MultipartFile image
//    );
//@PostMapping(path = "/add")
//ResponseEntity<String> addNewProduct(@RequestParam("image") MultipartFile image,
//                                     @RequestParam Map<String, String> requestMap);
//
//    @PostMapping(path = "/update")
//    ResponseEntity<String> updateProduct(@RequestParam(value = "image", required = false) MultipartFile image,
//                                         @RequestParam Map<String, String> requestMap);


    @GetMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

//    @PostMapping(path = "/update")
//    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

//    @PostMapping(path = "/updateStatus")
//    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @PutMapping("/update-status/{id}")
    ResponseEntity<?> updateMenuStatus(@PathVariable Integer id, @RequestBody Map<String, String> request);

    //    @GetMapping(path = "/shop/{shopId}")
//    ResponseEntity<List<ProductWrapper>> getProductsByShop(@PathVariable Integer shopId);
    @GetMapping("/shop/{shopId}")
    ResponseEntity<ShopProductsResponse> getProductsByShop(@PathVariable Integer shopId);


}
