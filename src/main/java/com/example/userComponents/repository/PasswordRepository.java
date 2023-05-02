package com.example.userComponents.repository;

import com.example.userComponents.model.PasswordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface PasswordRepository extends JpaRepository<PasswordUser, Integer> {
    PasswordUser getPasswordUserByUserId(int id);
}
