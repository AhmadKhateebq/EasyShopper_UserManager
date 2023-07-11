package com.example.userComponents.repository;

import com.example.userComponents.model.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokens, Integer> {
}
