package com.example.userComponents.service;

import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.userComponents.exceptions.InvalidPasswordException;
import com.example.userComponents.exceptions.UserNotFoundException;
import com.example.userComponents.exceptions.UsernameExistsException;
import com.example.userComponents.exceptions.WrongPasswordException;
import com.example.userComponents.model.AppUser;
import com.example.userComponents.model.PasswordUser;
import com.example.userComponents.repository.AppUserRepository;
import com.example.userComponents.util.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final PasswordService passwordService;
    private final ListNicknameService nicknameService;

    @Autowired
    public AppUserService(PasswordService passwordService, AppUserRepository repository, ListNicknameService nicknameService) {
        this.passwordService = passwordService;
        this.repository = repository;
        this.nicknameService = nicknameService;
    }

    public AppUser getUser(int id) throws Exception {
        AppUser user = repository.findById (id).orElseThrow (UserNotFoundException::new);
        if (user.getPictureUrl () == null || user.getPictureUrl ().isEmpty ()) {
            user.setPictureUrl ("https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg");
            return updateUser (user.getId (), user);
        }
        return user;
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
        passwordService.deleteUser (id);
        repository.deleteById (id);
        nicknameService.deleteAllNicknameByUserId (id);
    }

    public AppUser updateUser(int id, AppUser updatedUser) throws ResourceNotFoundException, UsernameExistsException {
        AppUser appUser = repository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException ("Product", "id", (long) id));
        if (userNameExists (updatedUser.getUsername ())) {
            throw new UsernameExistsException ();
        } else {
            appUser.setUsername (updatedUser.getUsername ());
        }
        appUser.setUsername (updatedUser.getUsername ());
        appUser.setPictureUrl (updatedUser.getPictureUrl ());

        appUser.setFname (updatedUser.getFname ());
        appUser.setLname (updatedUser.getLname ());
        appUser.setEmail (updatedUser.getEmail ());
        appUser.setFacebookId (updatedUser.getFacebookId ());
        appUser.setGoogleId (updatedUser.getGoogleId ());
        return repository.save (appUser);
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

    public void updatePassword(int id, String currentPassword, String newPassword) throws WrongPasswordException, InvalidPasswordException {
        PasswordUser user = passwordService.getUser(id);
        // Verify current password
        BCryptPasswordEncoder passwordEncoder = PasswordEncryptor.getInstance();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new WrongPasswordException("Current password is incorrect");
        }
        // Check if new password is the same as current password
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new InvalidPasswordException("New password must be different from the current password");
        }
        // Encrypt and update new password
        user.setPassword(newPassword);
        passwordService.saveUser(user);
    }

}
