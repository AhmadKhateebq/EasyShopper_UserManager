package com.example.userComponents.service;

import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final PasswordService passwordService;

    @Autowired
    public AppUserService(PasswordService passwordService, AppUserRepository repository) {
        this.passwordService = passwordService;
        this.repository = repository;
    }

    public AppUser getUser(int id) throws Exception {
        return repository.findById (id).orElseThrow (UserNotFoundException::new);
    }

    public AppUser getUserByUsername(String username) {
        return repository.getAppUserByUsername (username);
    }

    public List<AppUser> getAllUsers() {
        return repository.findAll ();
    }

    public AppUser saveUser(AppUser user) {
        return repository.save (user);
    }

    public void deleteUserById(int id) throws UserNotFoundException {
        passwordService.deleteUser (passwordService.getUser (id));
        repository.deleteById (id);
    }
    public AppUser updateUser(int id, AppUser updatedUser)throws ResourceNotFoundException {
        AppUser appUser = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id",(long) id));
        appUser.setPictureUrl (updatedUser.getPictureUrl ());
        appUser.setUsername (updatedUser.getUsername ());
        appUser.setFname (updatedUser.getFname ());
        appUser.setLname (updatedUser.getLname ());
        appUser.setEmail (updatedUser.getEmail ());
        appUser.setFacebookId (updatedUser.getFacebookId ());
        appUser.setGoogleId (updatedUser.getGoogleId ());
        return repository.save(appUser);
    }

    public AppUser getUserByFacebookId(String id) {
        return repository.getAppUserByFacebookId (id);
    }

    public AppUser getUserByGoogleId(String id) {
        return repository.getAppUserByGoogleId (id);
    }

    public boolean userNameExists(String username) {
        return getUserByUsername (username).getUsername ().equals (username);
    }
}
