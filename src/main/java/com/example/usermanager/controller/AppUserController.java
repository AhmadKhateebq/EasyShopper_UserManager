package com.example.usermanager.controller;

import com.example.usermanager.model.AppUser;
import com.example.usermanager.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    private AppUserService service;
    @GetMapping("{id}")
    AppUser getUser(@PathVariable int id){
        try {
            return service.getUser (id);
        } catch (Exception e) {
            return new AppUser ();
        }
    }
    @GetMapping
    List<AppUser> getAllUsers(){
        return service.getAllUsers ();
    }
    @PostMapping
    AppUser addUser(@RequestBody AppUser appUser){
        return service.saveUser (appUser);
    }
    @DeleteMapping("{id}")
    void deleteUser(@PathVariable int id){
        service.deleteUserById (id);
    }
    @DeleteMapping
    void deleteUser(@RequestBody AppUser appUser){
        service.deleteUser (appUser);
    }
}
