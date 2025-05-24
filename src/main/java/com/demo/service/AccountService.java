package com.demo.service;

import com.demo.POJO.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Account updateAccount(Long id, Account account);
    void deleteAccount(Long id);
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    Account findByEmail(String email);

}

