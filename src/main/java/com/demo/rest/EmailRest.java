package com.demo.rest;

import com.demo.POJO.Email;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//@CrossOrigin(origins = "*") // Allow all origins
public interface EmailRest {
    Email sendEmail(Email email);
    Email saveEmail(Email email);
//    @PostMapping("/send")
//    Email saveEmail(@RequestBody Email email);
}
