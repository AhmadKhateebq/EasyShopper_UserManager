package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories(
        basePackages = {"com.example.usermanager"}
)
@ComponentScan("com.example.itemmanager")
@ComponentScan("com.example.usermanager")
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run (ApplicationStart.class, args);
    }

}
