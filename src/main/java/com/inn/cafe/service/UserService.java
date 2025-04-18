package com.inn.cafe.service;

import com.inn.cafe.POJO.User;
import com.inn.cafe.dto.SignUpDto;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    String signUp(SignUpDto signUpDto);
    String login(Map<String, String> requestMap);
    List<User> getAllUser();
//    ResponseEntity<String> update (Map<String, String> requestMap);
//    ResponseEntity<String> checkToken();
    String changePassword(Map<String, String> requestMap);
//    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
