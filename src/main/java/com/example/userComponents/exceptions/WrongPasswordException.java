package com.example.userComponents.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(String message) {
        super (message);
    }
    public WrongPasswordException(){super();}
}
