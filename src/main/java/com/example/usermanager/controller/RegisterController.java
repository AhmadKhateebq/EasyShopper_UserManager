package com.example.usermanager.controller;

import com.example.usermanager.service.RegisterService;
import com.example.usermanager.util.UserDto;
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
