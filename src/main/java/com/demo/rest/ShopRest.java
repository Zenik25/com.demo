package com.demo.rest;

//import com.demo.POJO.Category;
import com.demo.POJO.Shop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping(path = "/shop")
public interface ShopRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewShop(
            @RequestParam Map<String, String> requestMap,
            @RequestParam("image") MultipartFile file);
    @GetMapping(path = "/get")
    ResponseEntity<List<Shop>> getAllShop(@RequestBody(required = false)String filterValue);


    @PostMapping(path = "/update")
    ResponseEntity<String> updateShop(
            @RequestParam Map<String, String> requestMap,
            @RequestParam(value = "image", required = false) MultipartFile file);

    ResponseEntity<String> deleteShop(Integer id);

    @GetMapping("/shop-name/{shopId}")
    ResponseEntity<String> getShopName(@PathVariable Integer shopId);

}