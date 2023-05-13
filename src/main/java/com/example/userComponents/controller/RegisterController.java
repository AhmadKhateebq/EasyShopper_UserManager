package com.example.userComponents.controller;

import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.service.RegisterService;
import com.example.userComponents.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    RegisterService service;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok (service.register (userDto));
        }catch (UserNotFoundException e1){
            return ResponseEntity.status (418).build ();
        }

    }
}
