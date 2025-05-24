package com.demo.dao;

import com.demo.POJO.Product;
import com.demo.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<ProductWrapper>getAllProduct();
    @Query(name = "Product.getAllProductByShopId")
    List<ProductWrapper> getProductsByShopId(@Param("shopId") Integer shopId);
    List<Product> findByShopId(Integer shopId);



    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

    ProductWrapper getProductById(@Param("id") Integer id);
    Product findByName(String name);  // NOT findByProductName

}
