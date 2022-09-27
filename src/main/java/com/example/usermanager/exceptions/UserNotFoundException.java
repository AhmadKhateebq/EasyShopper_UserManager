package com.example.usermanager.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super (message);
    }

    public UserNotFoundException() {
    }
}
