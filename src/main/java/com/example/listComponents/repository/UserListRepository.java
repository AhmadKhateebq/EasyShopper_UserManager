package com.example.listComponents.repository;

import com.example.listComponents.model.UserList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserListRepository extends MongoRepository<UserList, Long> {
}
