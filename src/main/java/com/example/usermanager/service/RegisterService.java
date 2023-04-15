package com.example.usermanager.service;

import com.example.usermanager.model.AppUser;
import com.example.usermanager.model.PasswordUser;
import com.example.usermanager.util.UserDto;
import com.example.usermanager.util.mapper.UserPasswordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    AppUserService appUserService;
    @Autowired
    PasswordService passwordService;

    public void register(UserDto dto) {
        if (appUserService.getUserByUsername (dto.getUsername ()) == null) {
            PasswordUser passwordUser = new PasswordUser ();
            AppUser appUser = appUserService.saveUser (UserPasswordMapper.USER_PASSWORD_DTO (dto));;
            passwordUser.setPassword (dto.getPassword ());
            passwordUser.setUser (appUserService.getUserByUsername (dto.getUsername ()));
            passwordService.saveUser (passwordUser);
        }
    }

}
