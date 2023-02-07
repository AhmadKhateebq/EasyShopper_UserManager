package com.example.usermanager.model;

import com.example.usermanager.model.enums.ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {
    @Id
    int id;
    String username;
    String fname;
    String lname;
    String email;
//    String password;
    @Column(name = "roles")
    ROLE role;
}
