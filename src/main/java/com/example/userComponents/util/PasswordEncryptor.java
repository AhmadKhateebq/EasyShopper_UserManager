package com.example.userComponents.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
    static BCryptPasswordEncoder passwordPEncoder = new BCryptPasswordEncoder ();
    private PasswordEncryptor(){
    }

    public static BCryptPasswordEncoder getInstance() {
        return passwordPEncoder;
    }
}
