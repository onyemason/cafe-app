package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtAuthenticationFilter;
import com.inn.cafe.JWT.JwtTokenProvider;
import com.inn.cafe.POJO.User;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.dto.SignUpDto;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service

public class UserServiceImpl implements UserService {

    UserDao userDao;


    PasswordEncoder passwordEncoder;

    CustomUserDetailsService customUserDetailsService;


    JwtTokenProvider jwtTokenProvider;

    JwtAuthenticationFilter jwtAuthenticationFilter;


    AuthenticationManager authenticationManager;

    EmailUtils emailUtils;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, JwtTokenProvider jwtTokenProvider, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationManager authenticationManager, EmailUtils emailUtils) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationManager = authenticationManager;
        this.emailUtils = emailUtils;
    }

    @Override
    public String signUp(SignUpDto signUpDto) {
        log.info("Inside signup {}", signUpDto);
        if(userDao.existsByEmail(signUpDto.getEmail())){
            return "Email already exist";
        }
        if(userDao.existsByContactNumber(signUpDto.getContactNumber())){
            return "Phone number Already exist";
        }
        String message;

        try {
            User user = new User();
            user.setName(signUpDto.getName());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            user.setContactNumber(signUpDto.getContactNumber());
            user.setStatus("true");
            user.setRole("user");
            userDao.save(user);
            message= "user saved successfully";

        }catch (Exception e){
            message = e.getMessage();
        }
        return message;

    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword((passwordEncoder.encode(requestMap.get("password")))); // passwordEncoder.encode
        user.setStatus("false");
        user.setRole("user");
        return user;

    }

    @Override
    public String login(Map<String, String> requestMap) {
        String jwtToken;
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            jwtToken=  jwtTokenProvider.generateToken(authentication);
        }catch (BadCredentialsException e){
            return  e.getMessage();
        }

        return jwtToken;
    }

    @Override
    public List<User> getAllUser() {
        try {
            return userDao.getAllUsers();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    @Override
//    public ResponseEntity<String> update(Map<String, String> requestMap) {
//        try {
//            Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
//            if (!optional.isEmpty()) {
//                userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
//                sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAlLAdmin());
//                return CafeUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
//
//            } else {
//                return CafeUtils.getResponseEntity("User id doesn't exist", HttpStatus.OK);
//            }
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtAuthenticationFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtAuthenticationFilter.getCurrentUser(), "Account Approved", "USER:-" + user + " \n is approved by \nADMIN:-" + jwtAuthenticationFilter.getCurrentUser(), allAdmin);
        } else {
            emailUtils.sendSimpleMessage(jwtAuthenticationFilter.getCurrentUser(), "Account Disabled", "USER:-" + user + " \n is disabled by \nADMIN:-" + jwtAuthenticationFilter.getCurrentUser(), allAdmin);

        }
    }

//    @Override
//    public ResponseEntity<String> checkToken() {
//        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
//    }

    @Override
    public String changePassword(Map<String, String> requestMap) {
       String email =  SecurityContextHolder.getContext().getAuthentication().getName();
       Optional<User> user = userDao.findByEmail(email);
        System.err.println(user);
       String message="";
       if(!user.isEmpty()){
           if(passwordEncoder.encode(requestMap.get("oldPassword")).equals(user.get().getPassword())){
               userDao.updatePassword(requestMap.get("newPassword"), user.get().getEmail());
               message = "Password was changed successfully";
           }else{
               message="Your password is incorrect";
           }
       }
        return message;
    }

//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try{
//            Optional<User> user = userDao.findByEmail(requestMap.get("email"));
//            if(!Objects.isNull(user) && !Strings.isBlank(user.get().getEmail())){
//                emailUtils.forgetMail(user.get().getEmail(),"Credentials by Cafe Management System",user.get().getPassword());
//            }
//                return CafeUtils.getResponseEntity("Check Your mail for Credentials.",HttpStatus.OK);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}