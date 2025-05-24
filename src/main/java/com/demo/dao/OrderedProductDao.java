package com.demo.dao;

import com.demo.POJO.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductDao extends JpaRepository<OrderedProduct, Long> {
}
