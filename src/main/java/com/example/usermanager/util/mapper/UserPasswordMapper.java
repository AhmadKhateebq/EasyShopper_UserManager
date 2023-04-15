package com.example.usermanager.util.mapper;

import com.example.usermanager.model.AppUser;
import com.example.usermanager.model.PasswordUser;
import com.example.usermanager.util.UserDto;

public interface UserPasswordMapper {
    static UserDto USER_PASSWORD_DTO(AppUser user, PasswordUser passwordUser){
        UserDto dto = new UserDto ();
        dto.setPassword (passwordUser.getPassword ());
        dto.setEmail (user.getEmail ());
        dto.setFname (user.getFname ());
        dto.setUsername (user.getUsername ());
        dto.setLname (user.getLname ());
        return dto;
    }
    static AppUser USER_PASSWORD_DTO(UserDto dto){
        AppUser appUser = new AppUser ();
        appUser.setEmail (dto.getEmail ());
        appUser.setFname (dto.getFname ());
        appUser.setUsername (dto.getUsername ());
        appUser.setLname (dto.getLname ());
        return appUser;
    }
}
