package com.example.usermanager.repository;

import com.example.usermanager.model.PasswordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface PasswordRepository extends JpaRepository<PasswordUser, Integer> {
    PasswordUser getPasswordUserByUserId(int id);
}
