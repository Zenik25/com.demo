package com.demo.servicelmpl;

import com.demo.JWT.JwtFilter;
import com.demo.POJO.Category;
import com.demo.constents.ResConstants;
import com.demo.dao.CategoryDao;
import com.demo.service.CategoryService;
import com.demo.utils.ResUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.nio.file.*;


@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;


@Override
public ResponseEntity<String> addNewCategory(@RequestParam Map<String, String> requestMap) {
//                                             @RequestParam("file") MultipartFile file) {
    try {
        // ✅ Validate input fields
        if (!validateCategoryMap(requestMap, false)) {
            return ResUtils.getResponseEntity("Invalid Data", HttpStatus.BAD_REQUEST);
        }

        // ✅ Save image to disk or filesystem
//        String imagePath = saveImage(file);

        // ✅ Convert request map to Category entity and set image path
        Category category = getCategoryFromMap(requestMap, false);
//        category.setImage(imagePath);

        // ✅ Save to database
        categoryDao.save(category);

        return ResUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);

    } catch (Exception ex) {
        ex.printStackTrace();
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
//            , MultipartFile file) {
        try {

                if (validateCategoryMap(requestMap, true)) {
                    Optional<Category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (optional.isPresent()) {
                        Category category = optional.get();

                        // Update image only if a new file is provided
//                        if (file != null && !file.isEmpty()) {
//                            String imagePath = saveImage(file);
//                            category.setImage(imagePath);
//                        }

                        category.setName(requestMap.get("name"));
                        categoryDao.save(category);

                        return ResUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
                    } else {
                        return ResUtils.getResponseEntity("Category ID does not exist", HttpStatus.OK);
                    }
                }
                return ResUtils.getResponseEntity(ResConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public boolean deleteCategory(Integer id) {
        try {
            // Check if category exists
            if (categoryDao.existsById(id)) {
                categoryDao.deleteById(id);  // Delete category by ID
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return false;
        }
    }


    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
//            if (requestMap.containsKey("image")) {
                if (requestMap.containsKey("id") && validateId) {
                    return true;
                } else if (!validateId) {
                    return true;
                }
//            }
        }
        return false;
    }
    private  Category getCategoryFromMap(Map<String,String> requestMap,Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));

        }
        category.setName(requestMap.get("name") );
        return category;


    }
    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return  new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);

            }
            return  new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
//        try{
//
//                if (validateCategoryMap(requestMap,true)){
//                    Optional optional= categoryDao.findById(Integer.parseInt(requestMap.get("id")));
//                    if (!optional.isEmpty()){
//                        categoryDao.save(getCategoryFromMap(requestMap,true));
//                        return  ResUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
//                    }
//                    else {
//                        return ResUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
//                    }
//                }
//                return ResUtils.getResponseEntity(ResConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    private String saveImage(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }

        String uploadDir = "./uploads/category-images/"; // Directory for storing images
        Path uploadPath = Paths.get(uploadDir);

        // Create directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Unique file name
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uploadDir + fileName; // Return relative path
    }


}
