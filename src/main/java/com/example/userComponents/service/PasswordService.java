package com.example.userComponents.service;


import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {
    @Autowired
    private PasswordRepository repository;
    public PasswordUser getUser(int id) throws UserNotFoundException {
        PasswordUser user = repository.findById (id).orElseThrow (UserNotFoundException::new);
        String password = user.getPassword();
        //decode password
        user.setPassword (password);
        return user;
    }

    public List<PasswordUser> getAllUsers() {
        return repository.findAll ();
    }

    public void saveUser(PasswordUser user) {
        //password encryption
        BCryptPasswordEncoder passwordPEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordPEncoder.encode(user.getPassword());
        System.out.println (encodedPassword);
        user.setPassword(encodedPassword);
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
