package com.example.userComponents.controller;

import com.example.userComponents.model.UserTokens;
import com.example.userComponents.service.PushNotificationService;
import com.example.userComponents.service.UserTokenService;
import com.example.userComponents.util.NotificationRequest;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final PushNotificationService notificationService;
    private final UserTokenService userTokenService;

    @Autowired
    public NotificationController(PushNotificationService notificationService, UserTokenService userTokenService) {
        this.notificationService = notificationService;
        this.userTokenService = userTokenService;
    }

    @PostMapping("/device")
    public void sendNotification(@RequestBody TokenNotificationAndMessage request) {
        String deviceToken = request.getToken ();
        String title = request.getTitle ();
        String message = request.getMessage ();

        try {
            notificationService.sendNotification (deviceToken, title, message);
        } catch (FirebaseMessagingException e) {
            // Handle any exception that occurs during notification sending
            e.printStackTrace ();
        }

    }

    @PostMapping("/list")
    public ResponseEntity<Object> handleLists(@RequestBody NotificationRequest request) {
        try {
            notificationService.sendNotification (request);
            return ResponseEntity.ok ("notification sent");
        } catch (Exception e) {
            e.printStackTrace ();
            return ResponseEntity.status (418).body ("user not found");
        }
    }

    @PostMapping("/list/{listId}")
    public ResponseEntity<Object> handleList(@PathVariable int listId) {
        try {
            notificationService.sendNotification (listId);
            return ResponseEntity.ok ("notification sent");
        } catch (Exception e) {
            e.printStackTrace ();
            return ResponseEntity.status (418).body ("user not found");
        }
    }

    @PostMapping("user")
    public UserTokens saveToken(@RequestBody UserTokens userTokens) {
        return userTokenService.addUserToken (userTokens);
    }
    @PostMapping("delete_user")
    public void deleteToken(@RequestBody UserTokens userTokens) {
         userTokenService.deleteUser (userTokens);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TokenNotificationAndMessage {
    String token;
    String title;
    String message;
}

