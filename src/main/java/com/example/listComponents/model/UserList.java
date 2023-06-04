package com.example.listComponents.model;

import com.example.marketComponents.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "List")
public class UserList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isPrivate;
    private int userId;
    private String name;
    private List<SharedWith> usersSharedWith;
    private List<Product> items;

    public UserList(Long id, boolean isPrivate, int userId, String name, List<Product> items) {
        this.id = id;
        this.isPrivate = isPrivate;
        this.userId = userId;
        this.name = name;
        this.items = items;
        this.usersSharedWith = new ArrayList<> ();
    }

    public UserList(Long id, boolean isPrivate, int userId, String name) {
        this.id = id;
        this.isPrivate = isPrivate;
        this.userId = userId;
        this.name = name;
        this.items = new ArrayList<> ();
        this.usersSharedWith = new ArrayList<> ();
    }

    public boolean sharedWith(int id) {
        return usersSharedWith
                .stream ()
                .filter (sharedWith -> sharedWith.contains (id))
                .toList ()
                .size () == 1;
    }
    public boolean sharedWithAndCanEdit(int id) {
        return usersSharedWith
                .stream ()
                .filter (sharedWith -> sharedWith.contains (id))
                .filter (sharedWith -> sharedWith.canEdit)
                .toList ()
                .size () == 1;
    }


}
