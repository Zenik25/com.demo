package com.demo.restlmpl;

import com.demo.constents.ResConstants;
import com.demo.dao.UserDao;
import com.demo.rest.UserRest;
import com.demo.service.UserService;
import com.demo.utils.ResUtils;
import com.demo.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*") // Allow all origins

@RestController
public class UserRestImpl implements UserRest {
@Autowired
UserService userService;
@Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            return userService.signUp(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            return userService.getAllUser();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return userService.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();

        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return  userService.checkToken();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassowrd(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassowrd(Map<String, String> requestMap) {
        try {
            return userService.forgotPassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    @Override
//    public ResponseEntity<List<UserWrapper>> getAllAdmin() {
//        try{
//            return userService.getAllAdmin();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }
@Override
public ResponseEntity<List<String>> getAllAdmin() {
    return ResponseEntity.ok(userService.getAllAdmin());
}


}
