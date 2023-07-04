package com.example.userComponents.service;

import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.exceptions.WrongPasswordException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.repository.AppUserRepository;
import com.example.userComponents.repository.PasswordRepository;
import com.example.userComponents.util.PasswordEncryptor;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    final
    PasswordService passwordService;
    final
    AppUserService appUserService;
    final
    JWTUtil jwtUtil;
    @Value("${data.admin.username}")
    String adminUser;
    @Value("${data.admin.password}")
    String adminPassword;
    final BCryptPasswordEncoder passwordEncoder = PasswordEncryptor.getInstance ();

    @Autowired
    public LoginService(PasswordService passwordService, AppUserService appUserService, JWTUtil jwtUtil) {
        this.appUserService = appUserService;
        this.passwordService = passwordService;
        this.jwtUtil = jwtUtil;
    }

    boolean checkPassword(int id, String password) {
        PasswordUser getPassword = passwordService.getUser (id);
        return (passwordEncoder.matches (password, getPassword.getPassword ()));
    }

    public String loginUserName(String username, String password) throws UserNotFoundException, WrongPasswordException {
        AppUser user = appUserService.getUserByUsername (username);
        if (user == null) {
            if (username.equals (adminUser) && password.equals (adminPassword))
                return "1477";
            else
                throw new UserNotFoundException ("user not found");
        }
        if (!checkPassword (user.getId (), password)) {
            throw new WrongPasswordException ("password is incorrect");
        } else {
            return jwtUtil.generateToken (username, user.getId ())+","+user.getId ();
        }
    }

}
