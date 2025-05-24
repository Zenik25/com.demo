package com.demo.servicelmpl;

import com.demo.POJO.Account;
import com.demo.POJO.Shop;
import com.demo.dao.AccountDao;
import com.demo.dao.ShopDao;
import com.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ShopDao shopDao;

    @Override
    public Account createAccount(Account account) {
        if (account.getShop() != null && account.getShop().getId() != null) {
            Optional<Shop> shopOptional = shopDao.findById(account.getShop().getId());
            if (shopOptional.isPresent()) {
                account.setShop(shopOptional.get());
            } else {
                throw new RuntimeException("Shop with ID " + account.getShop().getId() + " not found");
            }
        } else {
            throw new RuntimeException("Shop ID is required for account creation");
        }

        return accountDao.save(account);
    }


    public Account findByEmail(String email) {
        System.out.println("Looking for account with email: " + email);
        return accountDao.findByEmail(email);
    }


    @Override
    public Account updateAccount(Long id, Account updatedAccount) {
        Account account = accountDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setEmail(updatedAccount.getEmail());
        account.setPassword(updatedAccount.getPassword());
        account.setRole(updatedAccount.getRole());
        return accountDao.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountDao.deleteById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}

