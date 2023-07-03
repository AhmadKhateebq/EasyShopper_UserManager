package com.example.userComponents.controller;

import com.example.userComponents.exceptions.InvalidPasswordException;
import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.exceptions.UsernameExistsException;
import com.example.userComponents.exceptions.WrongPasswordException;
import com.example.userComponents.model.LoginUser;
import com.example.userComponents.model.PasswordUpdateRequest;
import com.example.userComponents.service.LoginService;
import com.example.userComponents.service.RegisterService;
import com.example.userComponents.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LoginAndRegisterController {
    final
    LoginService loginService;
    final
    RegisterService registerServices;
    @Autowired
    public LoginAndRegisterController(LoginService service, RegisterService registerServices) {
        this.loginService = service;
        this.registerServices = registerServices;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok (registerServices.register (userDto));
        }catch (UsernameExistsException e) {
            return ResponseEntity.status (HttpStatus.NOT_ACCEPTABLE).build ();
        }

    }
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginUser user){
        try {
            return ResponseEntity.ok (loginService.loginUserName (user.getUsername (), user.getPassword ()));
        }catch (UserNotFoundException e){
            return ResponseEntity.status (418).build ();
        }catch (WrongPasswordException e1){
            return ResponseEntity.status (401).build ();
        }

    }

}
