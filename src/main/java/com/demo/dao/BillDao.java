//package com.demo.dao;
//
//import com.demo.POJO.Bill;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface BillDao extends JpaRepository<Bill,Integer> {
//    List<Bill> getAllBills();
//    List<Bill> getBillByUserName(@Param("username")String username);
//
//
//}
package com.demo.dao;

import com.demo.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BillDao extends JpaRepository<Bill, Long> {
    Bill findByUuid(String uuid);
}

