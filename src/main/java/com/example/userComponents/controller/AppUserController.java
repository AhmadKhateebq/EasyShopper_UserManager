package com.example.userComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.annotation.UserSecured;
import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    private AppUserService service;
    @GetMapping("/{id}")
    @UserSecured
    AppUser getUser(@PathVariable int id){
        try {
            return service.getUser (id);
        } catch (Exception e) {
            return new AppUser ();
        }
    }
    @GetMapping
    @AdminSecured
    List<AppUser> getAllUsers(){
        return service.getAllUsers ();
    }
    @DeleteMapping("/{id}")
    @AdminSecured
    ResponseEntity<Object> deleteUser(@PathVariable int id){
        try {
            service.deleteUserById (id);
            return ResponseEntity.status (202).build ();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
    }
}
