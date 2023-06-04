package com.example.controller;

import com.example.util.CountUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Value("${data.jwt-key}")
    private String token;
    @Value("${data.admin-key}")
    private String adminToken;
    private final CountUtil countUtil;
    public AdminController(CountUtil countUtil) {
        this.countUtil = countUtil;
    }

    @ApiOperation(value = "get admin token")
    @GetMapping("/get-token")
    public String getJwtToken(){
        return token;
    }
    @GetMapping("/get-admin-token")
    public String getAdminToken(){
        return adminToken;
    }
    @GetMapping("/count/{id}")
    public long getCount(@PathVariable String id){
        return switch (id){
            case "user"->countUtil.getUserCont ();
            case "list"->countUtil.getListCount ();
            case "product"->countUtil.getProductCount ();
            case "market"->countUtil.getSupermarketCount ();
            default -> throw new IllegalStateException ("Unexpected value: " + id);
        };
    }

}
