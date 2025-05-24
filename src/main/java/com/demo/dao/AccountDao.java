package com.demo.dao;

import com.demo.POJO.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Long> {
//    Optional<Account> findByEmail(String email);
    Account findByEmail(String email);

}
