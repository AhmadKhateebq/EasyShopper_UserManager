package com.example.usermanager.service;


import com.example.usermanager.exceptions.UserNotFoundException;
import com.example.usermanager.model.PasswordUser;
import com.example.usermanager.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {
    @Autowired
    private PasswordRepository repository;

    public PasswordUser getUser(int id) throws Exception {
        return repository.findById (id).orElseThrow (UserNotFoundException::new);
    }

    public List<PasswordUser> getAllUsers() {
        return repository.findAll ();
    }

    public PasswordUser saveUser(PasswordUser user) {
        return repository.save (user);
    }

    public void deleteUserById(int id) {
        repository.deleteById (id);
    }

    public void deleteUser(PasswordUser user) {
        repository.delete (user);
    }
    public PasswordUser searchByUserId(int id){
        return repository.getPasswordUserByUserId (id);
    }
}
