package com.example.usermanager.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class BeanManager {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate ();
    }
}
