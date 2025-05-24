package com.demo.servicelmpl;

import com.demo.JWT.CustomerUserDetailsService;
import com.demo.JWT.JwtFilter;
import com.demo.JWT.JwtUtil;
import com.demo.POJO.User;
import com.demo.constents.ResConstants;
import com.demo.dao.UserDao;
import com.demo.service.UserService;
import com.demo.utils.EmailUtils;
import com.demo.utils.ResUtils;
import com.demo.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;


import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;



    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp {}", requestMap);
        try {


        if (validateSignUpMap(requestMap)) {
            User user = userDao.findByEmailId(requestMap.get("email"));
            if (Objects.isNull(user)) {
                userDao.save(getUserFromMap(requestMap));
                return ResUtils.getResponseEntity("Successful Register", HttpStatus.OK);
            } else {
                return ResUtils.getResponseEntity("Email already exits.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResUtils.getResponseEntity(ResConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
    } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }



    private boolean validateSignUpMap(Map<String, String> requestMap){
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password"))
        {
            return true;
        }
        return false;
    }

    private  User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("true");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()){
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(),
                            customerUserDetailsService.getUserDetails().getRole()) + "\"}", HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex) {
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials"+"\"}",
                HttpStatus.BAD_REQUEST);
    }

//    @Override
//    public ResponseEntity<List<UserWrapper>> getAllUser() {
//        try {
//            if (jwtFilter.isAdmin()){
//                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
@Override
public ResponseEntity<List<UserWrapper>> getAllUser() {
    try {
        return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
    } catch (Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @Override
    public List<String> getAllAdmin() {
        return userDao.getAllAdmin();
    }


    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
               Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return ResUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                }
                else {
                    return ResUtils.getResponseEntity("User id doesn't exist", HttpStatus.OK);
                }
            }else {
                return ResUtils.getResponseEntity(ResConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {



            allAdmin.remove(jwtFilter.getCurrentUser());
            if (status!=null && status.equalsIgnoreCase("true")){
                emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved","USER:- " + user + "\n is approved by \nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);

            }else{
                emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled","USER:- " + user + "\n is disabled by \nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);
            }
    }
    @Override
    public ResponseEntity<String> checkToken() {
        return ResUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj=userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return ResUtils.getResponseEntity("Password Updated Successfully",HttpStatus.OK);
                }
                return ResUtils.getResponseEntity("Incorrect Old Password",HttpStatus.BAD_REQUEST);
            }
            return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(),"Credentials by Restaurant Manangement System",user.getPassword());
            return ResUtils.getResponseEntity("Check your mail for Credentials.",HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
