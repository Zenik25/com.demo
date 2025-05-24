package com.demo.restlmpl;

import com.demo.POJO.Category;
import com.demo.constents.ResConstants;
import com.demo.dao.CategoryDao;
import com.demo.rest.CategoryRest;
import com.demo.service.CategoryService;
import com.demo.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
//@CrossOrigin(origins = "*") // Allow all origins
@RestController
public class CategoryRestImpl implements CategoryRest {

@Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseEntity<String> addNewCategory(
            @RequestParam Map<String, String> requestMap)
//            @RequestParam("image") MultipartFile file)
     {
        try {
            return categoryService.addNewCategory(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            List<Category> categories = categoryDao.findAll();
//            return new ResponseEntity<>(categories, HttpStatus.OK);
//            List<Category> categories = categoryService.getAllCategory();
//            categories.forEach(category -> category.setImage(category.getImage()));  // ✅ Fixes image path
//            return categories;
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(
            @RequestParam Map<String, String> requestMap)
//            @RequestParam(value = "image", required = false) MultipartFile file)
    {
        try {
            return categoryService.updateCategory(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        boolean isDeleted = categoryService.deleteCategory(id);

        if (isDeleted) {
            return ResponseEntity.ok("Category deleted successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to delete category");
        }
    }


}
