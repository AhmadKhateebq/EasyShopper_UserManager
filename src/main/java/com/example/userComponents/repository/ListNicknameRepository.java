package com.example.userComponents.repository;

import com.example.userComponents.model.ListNickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListNicknameRepository extends JpaRepository<ListNickname,Integer> {
    void deleteAllByUserId(int userId);
    void deleteAllByListId(long listId);
    Optional<ListNickname> findByUserIdAndListId(int userId, long listId);
    List<ListNickname>findAllByUserId(int userId);
    void deleteByUserIdAndListId(int userId,long listId);

}
