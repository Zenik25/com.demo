package com.demo.rest;


import com.demo.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping(path = "/category")
public interface CategoryRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(
            @RequestParam Map<String, String> requestMap);
//            @RequestParam("image") MultipartFile file);
    @GetMapping(path = "/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestBody(required = false)String filterValue);


    @PostMapping(path = "/update")
    ResponseEntity<String> updateCategory(
            @RequestParam Map<String, String> requestMap);
//            @RequestParam(value = "image", required = false) MultipartFile file);

    ResponseEntity<String> deleteCategory(Integer id);
}
