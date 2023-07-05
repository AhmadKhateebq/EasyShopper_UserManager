package com.example.listComponents.service;

import com.example.listComponents.model.SequenceCounter;
import com.example.listComponents.model.SharedWith;
import com.example.listComponents.model.UserList;
import com.example.listComponents.repository.SequenceRepository;
import com.example.listComponents.repository.UserListRepository;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserListService {
    private final UserListRepository userListRepository;
    private final ProductService productService;
    private final SequenceRepository sequenceRepository;

    public UserListService(UserListRepository userListRepository, ProductService productService, SequenceRepository sequenceRepository) {
        this.userListRepository = userListRepository;
        this.productService = productService;
        this.sequenceRepository = sequenceRepository;
    }

    public List<UserList> findAll() {
        return userListRepository.findAll ();
    }

    public UserList findById(Long id) throws ResourceNotFoundException {
        return userListRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException ("User list not found with id " + id));
    }

    public UserList save(UserList userList) {
        SequenceCounter sequenceCounter = sequenceRepository.findAll ().get (0);
        Long id = sequenceCounter.getCount ();
        userList.setId (id);
        sequenceCounter.setCount (sequenceCounter.getCount () + 1);
        sequenceRepository.save (sequenceCounter);
        return userListRepository.save (userList);
    }

    public List<UserList> getListsSharedWithUser(int id) {
        return userListRepository.findAll ()
                .stream ()
                .filter (userList -> !userList.isPrivate ())
                .filter (userList -> userList.sharedWith (id))
                .toList ();
    }

    public void shareListWith(long listId, int userId,boolean canEdit) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        list.getUsersSharedWith ().add (new SharedWith (userId,canEdit));
        userListRepository.save (list);
    }
    public void removeSharedListfrom(long listId, int userId) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        SharedWith userSharedWith = list.getUsersSharedWith ()
                .stream ()
                .filter (sharedWith -> sharedWith.contains (userId))
                .findFirst ()
                .orElseThrow (ResourceNotFoundException::new);

        list.getUsersSharedWith ().remove (userSharedWith);
        userListRepository.save (list);
    }
    public void makePublic(long listId,boolean isPublic) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        list.setPrivate (isPublic);
        userListRepository.save (list);
    }
    public void addItemToUserList(long listId,long itemId) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        list.getItems ().add (productService.getProductById (itemId));
        userListRepository.save (list);
    }
    public void removeItemFromUserList(long listId,long itemId) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        list.getItems ().remove (productService.getProductById (itemId));
        userListRepository.save (list);
    }
    public void deleteById(Long id) {
        userListRepository.deleteById (id);
    }

    public List<UserList> getUsersListByUserId(int id) {
        return userListRepository.getUserListByUserId (id);
    }
    public List<Product> getListItemsById(long listId) throws ResourceNotFoundException {
        return findById(listId).getItems ();
    }
    public UserList updateUserList(long id, UserList userListRequest) throws ResourceNotFoundException {
        UserList existingUserList = userListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserList not found with id: " + id));

        // Update the fields of the existing UserList with the values from userListRequest
        existingUserList.setName(userListRequest.getName());
        existingUserList.setPrivate (userListRequest.isPrivate ());
        existingUserList.setUsersSharedWith (userListRequest.getUsersSharedWith ());
        return userListRepository.save(existingUserList);
    }
}
