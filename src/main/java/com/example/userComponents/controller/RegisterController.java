package com.example.userComponents.controller;

import com.example.userComponents.service.RegisterService;
import com.example.userComponents.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    RegisterService service;
    @PostMapping("/register")
    public void register(@RequestBody UserDto userDto) {
        service.register (userDto);
    }
}
