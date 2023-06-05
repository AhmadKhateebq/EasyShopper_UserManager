package com.example.userComponents.repository;

import com.example.userComponents.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser getAppUserByUsername(String username);
    AppUser getAppUserByFacebookId(String id);
    AppUser getAppUserByGoogleId(String id);
}
