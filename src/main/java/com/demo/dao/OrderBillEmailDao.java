package com.demo.dao;

//mport com.demo.POJO.OrderBillEmail;
import com.demo.POJO.OrderBillEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBillEmailDao extends JpaRepository<OrderBillEmail, Long> {
}
