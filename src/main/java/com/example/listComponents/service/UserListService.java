package com.example.listComponents.service;

import com.example.listComponents.model.SequenceCounter;
import com.example.listComponents.model.UserList;
import com.example.listComponents.repository.SequenceRepository;
import com.example.listComponents.repository.UserListRepository;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.service.ProductService;
import com.example.userComponents.model.ListNickname;
import com.example.userComponents.service.ListNicknameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserListService {
    private final UserListRepository userListRepository;
    private final ProductService productService;
    private final SequenceRepository sequenceRepository;
    private final ListNicknameService nicknameService;

    @Autowired
    public UserListService(UserListRepository userListRepository, ProductService productService, SequenceRepository sequenceRepository, ListNicknameService nicknameService) {
        this.userListRepository = userListRepository;
        this.productService = productService;
        this.sequenceRepository = sequenceRepository;
        this.nicknameService = nicknameService;
    }

    public List<UserList> findAll() {
        return userListRepository.findAll ();
    }

    public UserList findById(Long id) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException ("User list not found with id " + id));
        String nicknames = nicknameService
                .getNickname (list.getUserId (), list.getId ());
        if (nicknames!=null && !nicknames.isBlank ()) {
            list.setName (nicknames);
        }
        return list;
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
        List<UserList> userLists = userListRepository.findAll ()
                .stream ()
                .filter (userList -> !userList.isPrivate ())
                .filter (userList -> userList.sharedWith (id))
                .toList ();
        List<ListNickname> nicknames = nicknameService
                .getNickname (id);
        List<Long> nicknamesId = nicknames
                .stream ()
                .map (ListNickname::getListId)
                .toList ();
        userLists.forEach (list -> {
            if (nicknamesId.contains (list.getId ())) {
                String name = nicknames.stream ()
                        .filter (nickname -> nickname.getListId () == list.getId ())
                        .findFirst ()
                        .orElse (new ListNickname ()).getNickname ();
                list.setName (name);
            }
        });
        return userLists;
    }

    public void addItemToUserList(long listId, long itemId) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        list.getItems ().add (productService.getProductById (itemId));
        userListRepository.save (list);
    }

    public void removeItemFromUserList(long listId, long itemId) throws ResourceNotFoundException {
        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
        list.getItems ().remove (productService.getProductById (itemId));
        userListRepository.save (list);
    }

    public void deleteById(Long id) {
        userListRepository.deleteById (id);
        nicknameService.deleteAllNicknameByListId (id);
    }

    public List<UserList> getUsersListByUserId(int id) {
        return userListRepository.getUserListByUserId (id);
    }

    public List<Product> getListItemsById(long listId) throws ResourceNotFoundException {
        return findById (listId).getItems ();
    }

    public void updateUserList(long id, UserList userListRequest) throws ResourceNotFoundException {
        UserList existingUserList = userListRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException ("UserList not found with id: " + id));
        existingUserList.setName (userListRequest.getName ());
        existingUserList.setPrivate (userListRequest.isPrivate ());
        existingUserList.setUsersSharedWith (userListRequest.getUsersSharedWith ());
        userListRepository.save (existingUserList);
    }
}
/*unused code
//    public void shareListWith(long listId, int userId,boolean canEdit) throws ResourceNotFoundException {
//        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
//        list.getUsersSharedWith ().add (new SharedWith (userId,canEdit));
//        userListRepository.save (list);
//    }
//    public void removeSharedListfrom(long listId, int userId) throws ResourceNotFoundException {
//        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
//        SharedWith userSharedWith = list.getUsersSharedWith ()
//                .stream ()
//                .filter (sharedWith -> sharedWith.contains (userId))
//                .findFirst ()
//                .orElseThrow (ResourceNotFoundException::new);
//
//        list.getUsersSharedWith ().remove (userSharedWith);
//        userListRepository.save (list);
//    }
//    public void makePublic(long listId,boolean isPublic) throws ResourceNotFoundException {
//        UserList list = userListRepository.findById (listId).orElseThrow (ResourceNotFoundException::new);
//        list.setPrivate (isPublic);
//        userListRepository.save (list);
//    }
 */
