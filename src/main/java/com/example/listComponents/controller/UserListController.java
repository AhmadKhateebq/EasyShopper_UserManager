package com.example.listComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.annotation.JwtSecured;
import com.example.annotation.UserSecured;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.listComponents.model.UserList;
import com.example.listComponents.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class UserListController {

    private final UserListService userListService;

    @Autowired
    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }
    @AdminSecured
    @GetMapping
    public List<UserList> getAllUserLists() {
        return userListService.findAll ();
    }
    @AdminSecured
    @GetMapping("/{id}")
    public UserList getUserListById(@PathVariable Long id) {

        try {
            return userListService.findById (id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException (e);
        }
    }
    @UserSecured
    @GetMapping("/u/{id}")
    public List<UserList> getUserListByUserId(@PathVariable int id) {
        return userListService.getUsersListByUserId (id);
    }
    @JwtSecured
    @PostMapping
    public UserList createUserList(@RequestBody UserList userList) {
        return userListService.save (userList);
    }
    @JwtSecured
    @PutMapping("/{id}")
    public UserList updateUserList(@PathVariable Long id, @RequestBody UserList userListRequest) {
        UserList userList;
        try {
            userList = userListService.findById (id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException (e);
        }
        userList.setName (userListRequest.getName ());
        return userListService.save (userList);
    }
    @AdminSecured
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserList(@PathVariable Long id) {
        userListService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }

    // other methods as needed
}
