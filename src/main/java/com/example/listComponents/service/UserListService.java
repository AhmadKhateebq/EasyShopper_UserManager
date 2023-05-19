package com.example.listComponents.service;

import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.listComponents.model.SequenceCounter;
import com.example.listComponents.model.UserList;
import com.example.listComponents.repository.SequenceRepository;
import com.example.listComponents.repository.UserListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserListService {
    private final UserListRepository userListRepository;
    private final SequenceRepository sequenceRepository;
    public UserListService(UserListRepository userListRepository, SequenceRepository sequenceRepository) {
        this.userListRepository = userListRepository;
        this.sequenceRepository = sequenceRepository;
    }
    public List<UserList> findAll() {
        return userListRepository.findAll();
    }

    public UserList findById(Long id) throws ResourceNotFoundException {
        return userListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("User list not found with id " + id));
    }

    public UserList save(UserList userList) {
        SequenceCounter sequenceCounter = sequenceRepository.findAll ().get (0);
        Long id = sequenceCounter.getCount ();
        userList.setId (id);
        sequenceCounter.setCount (sequenceCounter.getCount ()+1);
        sequenceRepository.save (sequenceCounter);
        return userListRepository.save(userList);
    }

    public void deleteById(Long id) {
        userListRepository.deleteById(id);
    }
    public List<UserList> getUsersListByUserId(int id){
        return userListRepository.getUserListByUserId (id);
    }
}
