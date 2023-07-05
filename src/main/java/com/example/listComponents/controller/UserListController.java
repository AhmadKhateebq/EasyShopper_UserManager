package com.example.listComponents.controller;

import com.example.annotation.AdminSecured;
import com.example.annotation.EditorSecured;
import com.example.annotation.UserSecured;
import com.example.annotation.ViewerSecured;
import com.example.listComponents.model.UserList;
import com.example.listComponents.service.UserListService;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserListController {
    private final UserListService userListService;

    @Autowired
    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @AdminSecured
    @GetMapping("/list")
    public List<UserList> getAllUserLists() {
        return userListService.findAll ();
    }

    @ViewerSecured
    @GetMapping("/list/{id}")
    public UserList getUserListById(@PathVariable Long id) {
        try {
            return userListService.findById (id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException (e);
        }
    }

    @UserSecured
    @GetMapping("/{userId}/list")
    public List<UserList> getUserListByUserId(@PathVariable int userId) {
        return userListService.getUsersListByUserId (userId);
    }

    @UserSecured
    @PostMapping("/{userId}/list")
    public ResponseEntity<Object> createUserList(@PathVariable int userId, @RequestBody UserList userList) {
        if (userId != userList.getUserId ())
            return ResponseEntity.status (423).build ();
        return ResponseEntity.ok (userListService.save (userList));
    }

    @UserSecured
    @PutMapping("/{userId}/list/{id}")
    public ResponseEntity<Object> updateUserList(@PathVariable int userId, @PathVariable Long id, @RequestBody UserList userListRequest) {
        try {
            userListService.updateUserList (id, userListRequest);
            return ResponseEntity.ok ().build ();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status (418).build ();
        }

    }

    @UserSecured
    @DeleteMapping("/{userId}/list/{id}")
    public ResponseEntity<?> deleteUserList(@PathVariable int userId, @PathVariable Long id) {
        userListService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }

    @ViewerSecured
    @GetMapping("list/{listId}/items")
    public ResponseEntity<List<Product>> getUserListItems(@PathVariable("listId") long listId) {
        try {
            return ResponseEntity.ok (userListService.getListItemsById (listId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound ().build ();
        }
    }

    @ViewerSecured
    @GetMapping("list/shared/userId")
    public ResponseEntity<List<UserList>> GetUserListSharedWithUser(@PathVariable("userId") int userId) {
        return ResponseEntity.ok (userListService.getListsSharedWithUser (userId));
    }

    @EditorSecured
    @PostMapping("/list/{listId}/items/{itemId}")
    public ResponseEntity<String> addItemToUserList(@PathVariable("listId") long listId, @PathVariable("itemId") long itemId) {
        try {
            userListService.addItemToUserList (listId, itemId);
            return ResponseEntity.ok ("Item added to the user list");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound ().build ();
        }
    }

    @EditorSecured
    @DeleteMapping("/list/{listId}/items/{itemId}")
    public ResponseEntity<String> removeItemFromUserList(@PathVariable("listId") long listId, @PathVariable("itemId") long itemId) {
        try {
            userListService.removeItemFromUserList (listId, itemId);
            return ResponseEntity.ok ("Item removed from the user list");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound ().build ();
        }
    }
}
