package com.example.usermanager.model;

import com.example.usermanager.model.enums.ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    String username;
    String fname;
    String lname;
    String email;
    String phoneNo;
    ROLE role;
}
