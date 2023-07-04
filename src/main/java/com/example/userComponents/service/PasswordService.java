package com.example.userComponents.service;


import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.repository.PasswordRepository;
import com.example.userComponents.util.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {
    private final PasswordRepository repository;
    BCryptPasswordEncoder passwordPEncoder = PasswordEncryptor.getInstance ();

    @Autowired
    public PasswordService(PasswordRepository repository) {
        this.repository = repository;
    }

    public PasswordUser getUser(int id) {
        return repository.getPasswordUserByUserId (id);
    }

    public void saveUser(PasswordUser user) {
        //password encryption
        String encodedPassword = passwordPEncoder.encode (user.getPassword ());
        user.setPassword (encodedPassword);
        repository.save (user);
    }

    public void deleteUser(int id) {
        repository.deleteById (id);
    }

}
