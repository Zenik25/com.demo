package com.demo.dao;

import com.demo.POJO.OrderBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBillDao extends JpaRepository<OrderBill, Integer> {
    List<OrderBill> findByShopId(Integer shopId);
}