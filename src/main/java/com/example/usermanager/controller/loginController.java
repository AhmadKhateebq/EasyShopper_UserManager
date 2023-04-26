package com.example.usermanager.controller;

import com.example.usermanager.model.LoginUser;
import com.example.usermanager.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
    String login(@RequestBody LoginUser user){
        return service.loginUserName (user.getUsername (), user.getPassword ());
    }
}
