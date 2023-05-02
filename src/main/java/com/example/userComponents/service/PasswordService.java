package com.example.userComponents.service;


import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.repository.PasswordRepository;
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

    public void saveUser(PasswordUser user) {
        repository.save (user);
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
