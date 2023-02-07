package com.example.usermanager.repository;

import com.example.usermanager.model.PasswordUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordUser, Integer> {
}
