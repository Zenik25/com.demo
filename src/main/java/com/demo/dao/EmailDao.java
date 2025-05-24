package com.demo.dao;
import com.demo.POJO.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDao extends JpaRepository<Email, Long> {
    // You can define custom queries here if needed
}