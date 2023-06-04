package com.example.userComponents.service;

import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final PasswordService passwordService;
    @Autowired
    public AppUserService(PasswordService passwordService, AppUserRepository repository) {
        this.passwordService = passwordService;
        this.repository = repository;
    }
    public AppUser getUser(int id) throws Exception {
        return repository.findById (id).orElseThrow (UserNotFoundException::new);
    }
    public AppUser getUserByUsername(String username) {
        return repository.getAppUserByUsername (username);
    }
    public List<AppUser> getAllUsers() {
        return repository.findAll ();
    }
    public AppUser saveUser(AppUser user) {
        return repository.save (user);
    }
    public void deleteUserById(int id) throws UserNotFoundException {
        passwordService.deleteUser (passwordService.getUser (id));
        repository.deleteById (id);
    }
    public boolean userNameExists(String username) {
        return getUserByUsername (username).getUsername ().equals (username);
    }
}
