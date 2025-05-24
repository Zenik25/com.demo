package com.demo.dao;

import com.demo.POJO.Delivery;
import com.demo.POJO.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
//    List<Order> findByShopId(Integer shopId);
    List<Order> getAllOrders();

    @Query("SELECT o FROM Order o WHERE o.shop.id = :shopId")
    List<Order> findByShopId(@Param("shopId") Integer shopId);

}
