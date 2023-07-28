package com.example.userComponents.service;

import com.example.userComponents.model.UserTokens;
import com.example.userComponents.repository.UserTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public UserTokenService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public UserTokens getUserTokenById(int id) {
        return userTokenRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException ("User token not found"));
    }
    public UserTokens getUserTokenByIdIfExists(int id) {
        return userTokenRepository.findById(id)
                .orElse (null);
    }

    public List<UserTokens> getAllUserTokens() {
        return userTokenRepository.findAll();
    }

    public void deleteUserToken(int id) {
        userTokenRepository.deleteById(id);
    }

    public UserTokens addUserToken(UserTokens userToken) {
        return userTokenRepository.save(userToken);
    }
    public void deleteUser(UserTokens userToken) {
         userTokenRepository.delete (userToken);
    }
}
