package com.example.marketComponents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ItemManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run (ItemManagerApplication.class, args);
    }
}
