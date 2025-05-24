package com.demo.servicelmpl;

import com.demo.JWT.JwtFilter;
import com.demo.constents.ResConstants;
import com.demo.dao.ShopDao;
import com.demo.POJO.Shop;
import com.demo.service.ShopService;
import com.demo.utils.ResUtils;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDao shopDao;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public Shop findById(Integer shopId) {
        return shopDao.findById(shopId).orElse(null); // Returns null if shop not found
    }

    @Override
    public ResponseEntity<String> addNewShop(@RequestParam Map<String, String> requestMap,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            // ✅ Validate input fields
            if (!validateShopMap(requestMap, false)) {
                return ResUtils.getResponseEntity("Invalid Data", HttpStatus.BAD_REQUEST);
            }

            // ✅ Save image to disk or filesystem
            String imagePath = saveImage(file);

            // ✅ Convert request map to Category entity and set image path
            Shop shop = getShopFromMap(requestMap, false);
            shop.setImage(imagePath);

            // ✅ Save to database
            shopDao.save(shop);

            return ResUtils.getResponseEntity("Shop Added Successfully", HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateShop(Map<String, String> requestMap, MultipartFile file) {
        try {

            if (validateShopMap(requestMap, true)) {
                Optional<Shop> optional = shopDao.findById(Integer.parseInt(requestMap.get("id")));
                if (optional.isPresent()) {
                    Shop shop = optional.get();

                    // Update image only if a new file is provided
                    if (file != null && !file.isEmpty()) {
                        String imagePath = saveImage(file);
                        shop.setImage(imagePath);
                    }

                    shop.setName(requestMap.get("name"));
                    shop.setPhone(requestMap.get("phone"));
                    shopDao.save(shop);

                    return ResUtils.getResponseEntity("Shop Updated Successfully", HttpStatus.OK);
                } else {
                    return ResUtils.getResponseEntity("Shop ID does not exist", HttpStatus.OK);
                }
            }
            return ResUtils.getResponseEntity(ResConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public boolean deleteShop(Integer id) {
        try {
            // Check if category exists
            if (shopDao.existsById(id)) {
                shopDao.deleteById(id);  // Delete category by ID
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


    private boolean validateShopMap(Map<String, String> requestMap, boolean validateId) {
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
    private  Shop getShopFromMap(Map<String,String> requestMap,Boolean isAdd){
        Shop shop = new Shop();
        if(isAdd){
            shop.setId(Integer.parseInt(requestMap.get("id")));

        }
        shop.setName(requestMap.get("name") );
        shop.setPhone(requestMap.get("phone"));
        return shop;


    }
    @Override
    public ResponseEntity<List<Shop>> getAllShop(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return  new ResponseEntity<List<Shop>>(shopDao.getAllShop(),HttpStatus.OK);

            }
            return  new ResponseEntity<>(shopDao.findAll(),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Shop>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateShop(Map<String, String> requestMap) {
        try{

            if (validateShopMap(requestMap,true)){
                Optional optional= shopDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    shopDao.save(getShopFromMap(requestMap,true));
                    return  ResUtils.getResponseEntity("Shop Updated Successfully", HttpStatus.OK);
                }
                else {
                    return ResUtils.getResponseEntity("Shop id does not exist", HttpStatus.OK);
                }
            }
            return ResUtils.getResponseEntity(ResConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private String saveImage(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }

        String uploadDir = "./uploads/shop-images/"; // Directory for storing images
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

