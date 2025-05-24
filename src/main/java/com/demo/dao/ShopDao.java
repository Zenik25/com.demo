package com.demo.dao;

import com.demo.POJO.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShopDao extends JpaRepository<Shop, Integer> {

    List<Shop> getAllShop();

}

