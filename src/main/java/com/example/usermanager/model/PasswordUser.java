package com.example.usermanager.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "passwords")
public class PasswordUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @JoinColumn(name = "user_id")
    @OneToOne
    @MapsId
    AppUser user;
    String password;
}
