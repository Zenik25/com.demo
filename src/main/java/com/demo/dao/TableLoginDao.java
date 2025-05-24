//package com.demo.dao;
//
//import com.demo.POJO.TableLogin;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//
//public interface TableLoginDao extends JpaRepository<TableLogin, String> {
//
//
//    TableLogin findByTableNumber(@Param("tableNumber")String tableNumber);
//}

package com.demo.dao;

import com.demo.POJO.TableLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableLoginDao extends JpaRepository<TableLogin, Integer> {
    List<TableLogin> findAll();
    Optional<TableLogin> findByTableNumberAndSeatAndPassword(String tableNumber, String seat, String password);
//    List<TableLogin> findByShopName(String shopName);
//List<TableLogin> findByShopId(Integer shopId);
    @Query("SELECT t FROM TableLogin t WHERE t.shop.id = :shopId")
    List<TableLogin> findByShopId(@Param("shopId") Integer shopId);


}
