package com.demo.rest;

import com.demo.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import  org. springframework. web. bind. annotation.CrossOrigin;

import java.util.Map;


import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*") // Allow all origins
@RequestMapping(path = "/user")
public interface UserRest {

@PostMapping(path = "/signup")
@CrossOrigin
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody(required = true)Map<String, String> requestMap);


    @GetMapping(path = "/get")
    @CrossOrigin
    public ResponseEntity<List<UserWrapper>>getAllUsers();

    @GetMapping("/admin")
    @CrossOrigin
    ResponseEntity<List<String>> getAllAdmin();

    @PostMapping(path = "/update")
    @CrossOrigin
    public ResponseEntity<String> update(@RequestBody(required = true)Map<String, String> requestMap);

    @GetMapping(path ="/checkToken" )
    @CrossOrigin
    ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    @CrossOrigin
    ResponseEntity<String> changePassowrd(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/forgotPassword")
    @CrossOrigin
    ResponseEntity<String> forgotPassowrd(@RequestBody Map<String, String> requestMap);
}
