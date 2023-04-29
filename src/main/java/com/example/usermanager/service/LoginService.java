package com.example.usermanager.service;

import com.example.usermanager.exceptions.UnauthorizedException;
import com.example.usermanager.exceptions.UserNotFoundException;
import com.example.usermanager.model.AppUser;
import com.example.usermanager.model.PasswordUser;
import com.example.usermanager.repository.AppUserRepository;
import com.example.usermanager.repository.PasswordRepository;
import com.example.usermanager.util.JWTUtil;
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

    public String loginUserName(String username, String password)throws UserNotFoundException {
        AppUser user = appUserRepository.getAppUserByUsername (username);

        if (user == null)
            throw new UserNotFoundException ("user not found");
        if (!checkPassword (user.getId (), password)) {
            throw new UserNotFoundException ("password is incorrect");
        } else {
            return jwtUtil.generateToken (username);
        }
    }

}
