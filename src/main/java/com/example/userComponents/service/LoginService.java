package com.example.userComponents.service;

import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.exceptions.WrongPasswordException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.repository.AppUserRepository;
import com.example.userComponents.repository.PasswordRepository;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    JWTUtil jwtUtil;

    boolean checkPassword(int id, String password) {
        PasswordUser getPassword = passwordRepository.getPasswordUserByUserId (id);
        return (getPassword.getPassword ().equals (password));
    }

    public String loginUserName(String username, String password) throws UserNotFoundException, WrongPasswordException {
        AppUser user = appUserRepository.getAppUserByUsername (username);
        if (user == null)
            throw new UserNotFoundException ("user not found");
        if (!checkPassword (user.getId (), password)) {
            throw new WrongPasswordException ("password is incorrect");
        } else {
            return jwtUtil.generateToken (username);
        }
    }

}
