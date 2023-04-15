package com.example.usermanager.util;

import lombok.Data;

@Data
public class UserDto {
    String username;
    String fname;
    String lname;
    String email;
    String password;
}
