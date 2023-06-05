package com.example.controller;

import com.example.service.FacebookLoginService;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.service.AppUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login/facebook")
public class FacebookLoginController {

    final
    AppUserService appUserService;
    final
    FacebookLoginService service;

    public FacebookLoginController(AppUserService appUserService, FacebookLoginService service) {
        this.appUserService = appUserService;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> login() {
        return ResponseEntity.status (HttpStatus.FOUND)
                .header (HttpHeaders.LOCATION, service.getUrl())
                .build ();
    }

    @GetMapping("/callback")
    public ResponseEntity<AppUser> callback(@RequestParam("code") String code) {
        return ResponseEntity.ok (service.callback (code));
    }
}

