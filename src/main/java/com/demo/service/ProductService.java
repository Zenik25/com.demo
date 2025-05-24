package com.demo.service;

import com.demo.POJO.Product;
import com.demo.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;

public interface ProductService {
//    ResponseEntity<String> addNewProduct(String name, String description, Integer price, Integer categoryId, MultipartFile image);
Product addProduct(String name, String description, Integer price,
                   Integer categoryId, MultipartFile image, Integer shopId);
    ResponseEntity<String> updateProduct(MultipartFile image, Map<String, String> requestMap);
    List<ProductWrapper> getProductsByShopId(Integer shopId);
    ResponseEntity<?> updateMenuStatus(Integer id, String status);

    ResponseEntity<List<ProductWrapper>> getAllProduct();

//    ResponseEntity<List<ProductWrapper>> getAllProduct();
//    ResponseEntity<String> updateProduct(Map<String,String>requestMap);
    ResponseEntity<String> deleteProduct(Integer id);
//    ResponseEntity<String> updateStatus(Map<String,String>requestMap);
//    ResponseEntity<List<ProductWrapper>> getByCategory(Integer id);
//    ResponseEntity<ProductWrapper> getProductById(Integer id);
}
