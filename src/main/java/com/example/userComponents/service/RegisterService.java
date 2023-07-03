package com.example.userComponents.service;

import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.exceptions.UsernameExistsException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.util.UserDto;
import com.example.userComponents.util.mapper.UserPasswordMapper;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    final
    AppUserService appUserService;
    final
    PasswordService passwordService;
    final
    JWTUtil jwtUtil;
    @Autowired
    public RegisterService(AppUserService appUserService, PasswordService passwordService, JWTUtil jwtUtil) {
        this.appUserService = appUserService;
        this.passwordService = passwordService;
        this.jwtUtil = jwtUtil;
    }

    public String register(UserDto dto) throws UsernameExistsException {
        if (appUserService.getUserByUsername (dto.getUsername ()) == null) {
            PasswordUser passwordUser = new PasswordUser ();
            AppUser appUser = appUserService.saveUser (UserPasswordMapper.USER_PASSWORD_DTO (dto));
            passwordUser.setPassword (dto.getPassword ());
            passwordUser.setUser (appUserService.getUserByUsername (dto.getUsername ()));
            passwordService.saveUser (passwordUser);
            return jwtUtil.generateToken (appUser.getUsername (), appUser.getId ());
        } else throw new UsernameExistsException ();
    }

}
