package com.example.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Value("${data.jwt-key}")
    private String token;
    @Value("${data.admin-key}")
    private String adminToken;
    @ApiOperation(value = "get admin token")
    @GetMapping("/get-token")
    String getJwtToken(){
        return token;
    }
    @GetMapping("/get-admin-token")
    String getAdminToken(){
        return adminToken;
    }
}
