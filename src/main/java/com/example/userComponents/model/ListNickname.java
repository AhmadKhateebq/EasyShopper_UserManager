package com.example.userComponents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ListNickname")
public class ListNickname {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "list_id")
    private long listId;
    private String nickname;
    public ListNickname(int userId,long listId,String nickname){
        setNickname (nickname);
        setListId (listId);
        setUserId (userId);
    }
}
