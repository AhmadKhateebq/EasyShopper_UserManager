package com.example.usermanager.service;

import com.example.usermanager.exceptions.UserNotFoundException;
import com.example.usermanager.model.AppUser;
import com.example.usermanager.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppUserService {
    @Autowired
    private AppUserRepository repository;

    public AppUser getUser(int id) throws Exception {
        return repository.findById (id).orElseThrow (UserNotFoundException::new);
    }

    public List<AppUser> getAllUsers() {
        return repository.findAll ();
    }

    public AppUser saveUser(AppUser user) {
        return repository.save (user);
    }

    public void deleteUserById(int id) {
        repository.deleteById (id);
    }

    public void deleteUser(AppUser user) {
        repository.delete (user);
    }
}
