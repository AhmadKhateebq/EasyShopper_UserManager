package com.example.usermanager.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException(String message) {
        super (message);
    }

    public UnauthorizedException() {
    }
}
