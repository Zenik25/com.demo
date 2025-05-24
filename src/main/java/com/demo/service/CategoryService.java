package com.demo.service;

import com.demo.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import  java.util.List;

public interface CategoryService {
    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);
    ResponseEntity<String> updateCategory(Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCategory(String filterValue);
//    ResponseEntity<String> updateCategory(Map<String,String>requestMap);
    boolean deleteCategory(Integer id);

}
