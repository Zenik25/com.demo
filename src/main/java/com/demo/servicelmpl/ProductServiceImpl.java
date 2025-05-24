package com.demo.servicelmpl;

import com.demo.JWT.JwtFilter;
import com.demo.POJO.Category;
import com.demo.POJO.Product;
import com.demo.POJO.Shop;
import com.demo.constents.ResConstants;
import com.demo.dao.CategoryDao;
import com.demo.dao.ProductDao;
import com.demo.dao.ShopDao;
import com.demo.service.ProductService;
import com.demo.utils.ResUtils;
import com.demo.wrapper.ProductWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


//    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryDao categoryDao;


    @Autowired
    private EntityManager entityManager;

    @Autowired
    public ProductServiceImpl(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
//    Integer shopId = jwtFilter.getCurrentUserShopId(); // Get the shopId from logged-in user
@Override
public List<ProductWrapper> getProductsByShopId(Integer shopId) {
    List<Product> products = productDao.findByShopId(shopId);
    return products.stream()
            .map(this::convertToWrapper)
            .collect(Collectors.toList());
}

    // Helper method to convert Product entity to ProductWrapper
    private ProductWrapper convertToWrapper(Product product) {
        return new ProductWrapper(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStatus(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getImage(),
                product.getShop().getId(),
                product.getShop().getName()
        );
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }
            else if(!validateId){
                return true;
            }

        }
        return false;
    }
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd){

            product.setId(Integer.parseInt(requestMap.get("id")));
        }
        else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;

    }


    @Autowired
    private ShopDao shopDao;

    @Override
    public Product addProduct(String name, String description, Integer price,
                              Integer categoryId, MultipartFile imageFile, Integer shopId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStatus("Available");

        // Set category
        Category category = categoryDao.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        // Set shop
        Shop shop = shopDao.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        product.setShop(shop);

        // Save image to disk and store filename
        String filename = saveImage(imageFile);
        product.setImage(filename);

        return productDao.save(product);
    }


//
//@Autowired
//ProductWrapper productWrapper;


//    @Override
//    public ResponseEntity<String> updateProduct(MultipartFile image, Map<String, String> requestMap) {
//        try {
//            // Safely extract and validate required values
//            String idStr = requestMap.get("id");
//            String name = requestMap.get("name");
//            String description = requestMap.get("description");
//            String priceStr = requestMap.get("price");
//            String categoryIdStr = requestMap.get("categoryId");
//            String shopIdStr = requestMap.get("shopId");
//
//            // Validate required fields
//            if (idStr == null || idStr.equalsIgnoreCase("null") ||
//                    priceStr == null || priceStr.equalsIgnoreCase("null") ||
//                    categoryIdStr == null || categoryIdStr.equalsIgnoreCase("null") ||
//                    shopIdStr == null || shopIdStr.equalsIgnoreCase("null")) {
//
//                return ResUtils.getResponseEntity("Missing required fields", HttpStatus.BAD_REQUEST);
//            }
//
//            Integer id = Integer.parseInt(idStr);
//            Integer price = Integer.parseInt(priceStr);
//            Integer categoryId = Integer.parseInt(categoryIdStr);
//            Integer shopId = Integer.parseInt(shopIdStr);
//
//            Optional<Product> optionalProduct = productDao.findById(id);
//            if (optionalProduct.isEmpty()) {
//                return ResUtils.getResponseEntity("Product not found", HttpStatus.NOT_FOUND);
//            }
//
//            Product product = optionalProduct.get();
//            product.setName(name);
//            product.setDescription(description);
//            product.setPrice(price);
//
//            // Set category
//            product.setCategory(categoryDao.findById(categoryId).orElse(null));
//
//            // Set shop
//            product.setShop(shopDao.findById(shopId).orElse(null)); // 🛠 ensure shopDao is injected
//
//            // Handle optional image
//            if (image != null && !image.isEmpty()) {
//                String filename = saveImage(image);
//                product.setImage(filename);
//            }
//
//            productDao.save(product);
//            return ResUtils.getResponseEntity("Product updated successfully", HttpStatus.OK);
//
//        } catch (NumberFormatException nfe) {
//            return ResUtils.getResponseEntity("Invalid number format", HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@Override
public ResponseEntity<String> updateProduct(MultipartFile image, Map<String, String> requestMap) {
    try {
        Integer id = Integer.parseInt(requestMap.get("id"));
        Optional<Product> optionalProduct = productDao.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResUtils.getResponseEntity("Product not found", HttpStatus.NOT_FOUND);
        }

        Product product = optionalProduct.get();

        // ✅ Set all fields
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        // ✅ Get and set category
        Integer categoryId = Integer.parseInt(requestMap.get("categoryId"));
        Category category = categoryDao.findById(categoryId).orElse(null);
        if (category == null) {
            return ResUtils.getResponseEntity("Category not found", HttpStatus.BAD_REQUEST);
        }
        product.setCategory(category);

        // ✅ Get and set shop
        if (!requestMap.containsKey("shopId") || requestMap.get("shopId").equals("null")) {
            return ResUtils.getResponseEntity("Shop ID is missing", HttpStatus.BAD_REQUEST);
        }

        Integer shopId = Integer.parseInt(requestMap.get("shopId"));
        Shop shop = shopDao.findById(shopId).orElse(null);
        if (shop == null) {
            return ResUtils.getResponseEntity("Shop not found", HttpStatus.BAD_REQUEST);
        }
        product.setShop(shop);

        // ✅ Handle image
        if (image != null && !image.isEmpty()) {
            String filename = saveImage(image);
            product.setImage(filename);
        }

        productDao.save(product);
        return ResUtils.getResponseEntity("Product updated successfully", HttpStatus.OK);

    } catch (Exception e) {
        e.printStackTrace();
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




    @Override
public ResponseEntity<List<ProductWrapper>> getAllProduct() {
    try {
        List<Product> products = productDao.findAll();
        List<ProductWrapper> productWrappers = new ArrayList<>();

        for (Product product : products) {
            ProductWrapper wrapper = new ProductWrapper();
            wrapper.setId(product.getId());
            wrapper.setName(product.getName());
            wrapper.setDescription(product.getDescription());
            wrapper.setPrice(product.getPrice());
            wrapper.setStatus(product.getStatus()); // ✅ Set status
            wrapper.setCategoryId(product.getCategory().getId());
            wrapper.setCategoryName(product.getCategory().getName()); // ✅ Set category name
            wrapper.setImage(product.getImage());
            productWrappers.add(wrapper);
        }

        return new ResponseEntity<>(productWrappers, HttpStatus.OK);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
}




    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {

                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()){
                    productDao.deleteById(id);
                    return ResUtils.getResponseEntity("Product Deleted Successfully",HttpStatus.OK);
                }
                return ResUtils.getResponseEntity("Product id does not exit.",HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateMenuStatus(Integer id, String status) {
        Optional<Product> optionalProduct = productDao.findById(id);

        if (!optionalProduct.isPresent()) {
            return ResUtils.getResponseEntity("Product not found", HttpStatus.NOT_FOUND);
        }

        Product product = optionalProduct.get();
        product.setStatus(status);
        productDao.save(product);

        return ResUtils.getResponseEntity("Product status updated successfully", HttpStatus.OK);
    }




    private String saveImage(MultipartFile imageFile) {
        try {
            String uploadDir = "./uploads/product-images/";
            String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs();

            Path filePath = Paths.get(uploadDir, filename);
            Files.write(filePath, imageFile.getBytes());

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }




}
