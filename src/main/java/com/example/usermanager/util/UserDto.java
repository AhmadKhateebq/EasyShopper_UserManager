package com.example.usermanager.util;

import com.example.usermanager.model.enums.ROLE;
import lombok.Data;

@Data
public class UserDto {
    String username;
    String fname;
    String lname;
    String email;
    String password;
    ROLE role;
}
