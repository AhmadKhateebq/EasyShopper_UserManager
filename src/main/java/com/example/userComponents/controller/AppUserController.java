package com.example.userComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.annotation.UserSecured;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.userComponents.exceptions.InvalidPasswordException;
import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.exceptions.UsernameExistsException;
import com.example.userComponents.exceptions.WrongPasswordException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.model.PasswordUpdateRequest;
import com.example.userComponents.service.AppUserService;
import com.example.userComponents.util.UsernameIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {
    private final AppUserService service;

    @Autowired
    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @UserSecured
    AppUser getUser(@PathVariable int id) {
        try {
            return service.getUser (id);
        } catch (Exception e) {
            return new AppUser ();
        }
    }

    @GetMapping
    @AdminSecured
    List<AppUser> getAllUsers() {
        return service.getAllUsers ();
    }

    @GetMapping("/usernames")
    List<UsernameIdDto> getAllUsernames() {
        return service.getAllUsers ().stream ()
                .map (
                        (user) -> new UsernameIdDto (user.getId (), user.getUsername ())
                ).toList ();
    }


    @DeleteMapping("/{id}")
    @AdminSecured
    ResponseEntity<Object> deleteUser(@PathVariable int id) {
        try {
            service.deleteUserById (id);
            return ResponseEntity.status (202).build ();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable("id") int id, @RequestBody AppUser updatedUser) {
        try {
            return ResponseEntity.ok (service.updateUser (id, updatedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest ().build ();
        } catch (UsernameExistsException e) {
            return ResponseEntity.status (HttpStatus.NOT_ACCEPTABLE).build ();
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable int id, @RequestBody PasswordUpdateRequest request) {
        try {
            service.updatePassword (id, request.getCurrentPassword (), request.getNewPassword ());
            return ResponseEntity.ok ("Password updated successfully");
        } catch (WrongPasswordException e) {//401
            return ResponseEntity.status (HttpStatus.UNAUTHORIZED).body ("Current password is incorrect");
        } catch (InvalidPasswordException e) {//406
            return ResponseEntity.status (HttpStatus.NOT_ACCEPTABLE).body ("New password must be different from the current password");
        }
    }
}
