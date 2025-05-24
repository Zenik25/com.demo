package com.demo.restlmpl;

import com.demo.POJO.Account;
import com.demo.dto.LoginResponse;
import com.demo.rest.AccountRest;
import com.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountRestImpl implements AccountRest {
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/id/{id}") // Renamed from "/{id}" to "/id/{id}"
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Account account) {
//        System.out.println("Login attempt: " + account.getEmail() + " / " + account.getPassword());
//        Account existingAccount = accountService.findByEmail(account.getEmail());
//
//        if (existingAccount == null) {
//            System.out.println("No account found with email: " + account.getEmail());
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//        }
//
//        if (!existingAccount.getPassword().equals(account.getPassword())) {
//            System.out.println("Password mismatch for: " + account.getEmail());
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//        }
//
//        return new ResponseEntity<>("Login successful as " + existingAccount.getRole(), HttpStatus.OK);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        System.out.println("Login attempt: " + account.getEmail() + " / " + account.getPassword());
        Account existingAccount = accountService.findByEmail(account.getEmail());

        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok(new LoginResponse(
                    existingAccount.getEmail(),
                    existingAccount.getRole(),
                    existingAccount.getShop()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


}
