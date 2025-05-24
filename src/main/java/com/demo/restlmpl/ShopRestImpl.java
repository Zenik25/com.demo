package com.demo.restlmpl;

import com.demo.POJO.Shop;
import com.demo.constents.ResConstants;
import com.demo.dao.ShopDao;
import com.demo.rest.ShopRest;
import com.demo.service.ShopService;
import com.demo.utils.ResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*") // Allow all origins
@RestController
public class ShopRestImpl implements ShopRest {

    @Autowired
    ShopDao shopDao;
    @Autowired
    ShopService shopService;
    @Override
    public ResponseEntity<String> addNewShop(
            @RequestParam Map<String, String> requestMap,
            @RequestParam("image") MultipartFile file)
    {
        try {
            return shopService.addNewShop(requestMap, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> getShopName(@PathVariable Integer shopId) {
        Shop shop = shopService.findById(shopId);
        if (shop != null) {
            return ResponseEntity.ok(shop.getName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found");
        }
    }


    @Override
    public ResponseEntity<List<Shop>> getAllShop(String filterValue) {
        try {
            List<Shop> shops = shopDao.findAll();
//            return new ResponseEntity<>(shop, HttpStatus.OK);
//            List<Category> categories = categoryService.getAllCategory();
            shops.forEach(shop -> shop.setImage(shop.getImage()));  // ✅ Fixes image path
//            return categories;
            return new ResponseEntity<>(shops, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateShop(
            @RequestParam Map<String, String> requestMap,
            @RequestParam(value = "image", required = false) MultipartFile file)
    {
        try {
            return shopService.updateShop(requestMap, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Integer id) {
        boolean isDeleted = shopService.deleteShop(id);

        if (isDeleted) {
            return ResponseEntity.ok("Shop deleted successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to delete shop");
        }
    }


}