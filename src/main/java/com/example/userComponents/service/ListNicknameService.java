package com.example.userComponents.service;

import com.example.userComponents.model.ListNickname;
import com.example.userComponents.repository.ListNicknameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListNicknameService {
    private final ListNicknameRepository listNicknameRepository;

    public ListNicknameService(ListNicknameRepository listNicknameRepository) {
        this.listNicknameRepository = listNicknameRepository;
    }
    public String getNickname(int userId,long listId){
        return listNicknameRepository.findByUserIdAndListId (userId, listId).orElse (new ListNickname ()).getNickname ();
    }
    public List<ListNickname> getNickname(int userId){
        return listNicknameRepository.findAllByUserId (userId);
    }


    public void deleteAllNicknameByUserId(int userId) {
        listNicknameRepository.deleteAllByUserId (userId);
    }

    public void deleteAllNicknameByListId(long listId) {
        listNicknameRepository.deleteAllByListId (listId);
    }

    public void changeOrSaveNickname(int userId, long listId, String name) {
        ListNickname nickname = listNicknameRepository.findByUserIdAndListId (userId, listId)
                .orElse (new ListNickname (userId,listId,name));
        nickname.setNickname (name);
        listNicknameRepository.save (nickname);
    }
    void deleteNickname(int userId,long listId){
        listNicknameRepository.deleteByUserIdAndListId (userId, listId);
    }
}
