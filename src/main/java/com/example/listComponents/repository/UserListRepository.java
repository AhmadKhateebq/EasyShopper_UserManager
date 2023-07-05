package com.example.listComponents.repository;

import com.example.listComponents.model.UserList;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserListRepository extends MongoRepository<UserList, Long> {
    List<UserList>getUserListByUserId(int id);

}
