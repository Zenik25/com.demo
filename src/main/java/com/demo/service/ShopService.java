package com.demo.service;

import com.demo.POJO.Shop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ShopService {
    ResponseEntity<String> addNewShop(Map<String, String> requestMap, MultipartFile file);
    ResponseEntity<String> updateShop(Map<String, String> requestMap, MultipartFile file);

    ResponseEntity<List<Shop>> getAllShop(String filterValue);
    ResponseEntity<String> updateShop(Map<String,String>requestMap);
    boolean deleteShop(Integer id);
    Shop findById(Integer shopId); // This method will return a Shop by its ID

}
