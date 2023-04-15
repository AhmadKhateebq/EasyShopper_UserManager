package com.example.usermanager.repository;

import com.example.usermanager.model.AppUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser getAppUserByUsername(String username);
}
