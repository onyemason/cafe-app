package com.inn.cafe.rest;

import com.inn.cafe.POJO.User;
import com.inn.cafe.dto.SignUpDto;
import com.inn.cafe.service.UserService;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(path = "/user")
public class     UserRest {
    private final UserService userService;

    public UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true)SignUpDto signUpDto){
        String message = userService.signUp(signUpDto);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @PostMapping(path="/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap){
        String response = userService.login(requestMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(path = "/get")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList = userService.getAllUser();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

//    @PostMapping(path = "/update")
//    public ResponseEntity<String> update (@RequestBody(required = true) Map<String, String> requestMap);

//    @GetMapping(path="/checkToken")
//    ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap){
        String response = userService.changePassword(requestMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping(path = ("/forgotPassword"))
//    ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);
}
