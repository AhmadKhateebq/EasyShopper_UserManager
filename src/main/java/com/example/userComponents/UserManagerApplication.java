package com.example.userComponents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class UserManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run (UserManagerApplication.class, args);
    }
}
