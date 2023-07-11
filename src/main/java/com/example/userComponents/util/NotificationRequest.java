package com.example.userComponents.util;

import com.example.listComponents.model.SharedWith;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    int userId;
    List<SharedWith> newShared;
    List<SharedWith> editShared;
}
