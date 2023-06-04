package com.example.listComponents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedWith {
    int userId;
    boolean canEdit;
    public boolean contains(int id){
        return this.userId == id;
    }
}
