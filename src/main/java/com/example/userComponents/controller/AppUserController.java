package com.example.userComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.annotation.UserSecured;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class AppUserController {
    @Autowired
    private AppUserService service;
    @GetMapping("{id}")
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
    @PostMapping
    AppUser addUser(@RequestBody AppUser appUser){
        return service.saveUser (appUser);
    }
    @DeleteMapping("{id}")
    @AdminSecured
    void deleteUser(@PathVariable int id){
        service.deleteUserById (id);
    }
}
