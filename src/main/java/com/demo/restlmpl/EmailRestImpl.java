package com.demo.restlmpl;

import com.demo.POJO.Email;
import com.demo.rest.EmailRest;
import com.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//@CrossOrigin(origins = "*") // Allow all origins
@RestController
@RequestMapping("/email")
public class EmailRestImpl implements EmailRest {

    private final EmailService emailService;

    @Autowired
    public EmailRestImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @PostMapping("/send")
    public Email sendEmail(@RequestBody Email email) {
        return emailService.saveEmail(email);  // Save the email and send it
    }

    @Override
    public Email saveEmail(Email email) {
        return emailService.saveEmail(email);
    }

//    @Override
//    public Email saveEmail(Email email) {
//        return emailService.saveEmail(email);  // Call the service to save and send the email
//    }
}
