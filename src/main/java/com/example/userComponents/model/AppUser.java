package com.example.userComponents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    String fname;
    String lname;
    String email;
    @Column(name = "facebook_id")
    String facebookId;
    @Column(name = "google_id")
    String googleId;
    @Column(name = "picture_url")
    String pictureUrl;
}
