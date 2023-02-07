package com.example.usermanager.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "passwords")
public class PasswordUser {
    @Id
    int id;
    @Column(name = "user_id")
    int userId;
    String password;
}
