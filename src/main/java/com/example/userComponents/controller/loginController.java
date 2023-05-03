package com.example.userComponents.controller;

import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.model.LoginUser;
import com.example.userComponents.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class loginController {
    @Autowired
    LoginService service;
    @PostMapping
    ResponseEntity<String> login(@RequestBody LoginUser user){
        try {
            return ResponseEntity.ok (service.loginUserName (user.getUsername (), user.getPassword ()));
        }catch (UserNotFoundException e){
            return ResponseEntity.status (418).build ();
        }

    }
}
