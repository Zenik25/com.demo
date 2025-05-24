
package com.demo.dao;

import com.demo.POJO.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDao extends JpaRepository<Delivery, Long> {
    List<Delivery> findByEmail(String email);
    @Query("SELECT d FROM Delivery d WHERE d.shop.id = :shopId")
    List<Delivery> findByShopId(@Param("shopId") Integer shopId);

}
