package com.demo.restlmpl;

import com.demo.JWT.JwtFilter;
import com.demo.POJO.Product;
import com.demo.POJO.Shop;
import com.demo.constents.ResConstants;
import com.demo.dto.ProductRequest;
import com.demo.dto.ShopProductsResponse;
import com.demo.dto.ShopTablesResponse;
import com.demo.rest.ProductRes;
import com.demo.service.ProductService;
import com.demo.service.ShopService;
import com.demo.utils.ResUtils;
import com.demo.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping(path = "/product")
@RestController
public class ProductResImpl implements ProductRes {

    @Autowired
    ProductService productService;
    @Autowired
    private ShopService shopService;

    @Override
    public ResponseEntity<ShopProductsResponse> getProductsByShop(@PathVariable Integer shopId) {

        try {

            List<ProductWrapper> products = productService.getProductsByShopId(shopId);
            Shop shop = shopService.findById(shopId);

            if (shop == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            ShopProductsResponse response = new ShopProductsResponse(shop.getName(), products);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }




//    @Override
//    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
//        try{
//            return  productService.addNewProduct(requestMap);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @Override
//    public ResponseEntity<String> addNewProduct(String name, String description, Integer price, Integer categoryId, MultipartFile image) {
//        try {
//            return productService.addNewProduct(name, description, price, categoryId, image);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Override
//    public ResponseEntity<String> addNewProduct(MultipartFile image, Map<String, String> requestMap) {
//        try {
//            return productService.addNewProduct(image, requestMap);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Override
//    public ResponseEntity<String> updateProduct(MultipartFile image, Map<String, String> requestMap) {
//        try {
//            return productService.updateProduct(image, requestMap);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
@Autowired
private JwtFilter jwtFilter;
//    @Autowired
//    private ProductService productService;


    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Integer price,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("shopId") Integer shopId
    ) {
        productService.addProduct(name, description, price, categoryId, image, shopId);
        return ResponseEntity.ok("Product added successfully!");
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
                                                @RequestParam(value = "image", required = false) MultipartFile image,
                                                @RequestParam Map<String, String> requestMap) {
        try {
            requestMap.put("id", String.valueOf(id)); // Include ID in map

            // Check if shopId is present in the requestMap
            String shopIdStr = requestMap.get("shopId");
            if (shopIdStr == null || shopIdStr.equalsIgnoreCase("null")) {
                return ResUtils.getResponseEntity("Shop ID is missing", HttpStatus.BAD_REQUEST);
            }

            // Optional: clean invalid entries
            requestMap.entrySet().removeIf(e -> e.getValue() == null || e.getValue().equalsIgnoreCase("null"));

            return productService.updateProduct(image, requestMap);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return productService.getAllProduct();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
//       try{
//            return productService.updateProduct(requestMap);
//       }catch (Exception ex){
//           ex.printStackTrace();
//       }
//       return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            return productService.deleteProduct(id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateMenuStatus(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            if (status == null || status.trim().isEmpty()) {
                return ResUtils.getResponseEntity("Status is required", HttpStatus.BAD_REQUEST);
            }

            return productService.updateMenuStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
