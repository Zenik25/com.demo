package com.demo.rest;

import com.demo.POJO.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountRest {
    ResponseEntity<Account> createAccount(Account account);
    ResponseEntity<Account> updateAccount(Long id, Account account);
    ResponseEntity<Void> deleteAccount(Long id);
    ResponseEntity<List<Account>> getAllAccounts();
    ResponseEntity<Account> getAccountById(Long id);
}
