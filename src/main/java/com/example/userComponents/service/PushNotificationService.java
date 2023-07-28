package com.example.userComponents.service;

import com.example.listComponents.model.SharedWith;
import com.example.listComponents.service.UserListService;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.userComponents.model.UserTokens;
import com.example.userComponents.util.NotificationRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PushNotificationService {

    private final FirebaseApp firebaseApp;
    private final UserListService userListService;
    private final UserTokenService userTokenService;
    private final AppUserService appUserService;


    @Autowired
    public PushNotificationService(FirebaseApp firebaseApp, UserListService userListService, UserTokenService userTokenService, AppUserService appUserService) {
        this.firebaseApp = firebaseApp;
        this.userListService = userListService;
        this.userTokenService = userTokenService;
        this.appUserService = appUserService;
    }

    public void sendNotification(String deviceToken, String title, String message) throws FirebaseMessagingException {
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance (firebaseApp);

        Message notificationMessage = Message.builder ()
                .setToken (deviceToken)
                .setNotification (Notification.builder ().setTitle (title).setBody (message).build ())
                .build ();

        String response = firebaseMessaging.send (notificationMessage);
        System.out.println ("Successfully sent message: " + response);
    }

    private List<UserTokens> getUserTokensForList(long listId) throws ResourceNotFoundException {
        List<Integer> userIds = userListService.findById (listId)
                .getUsersSharedWith ().stream ()
                .map (SharedWith::getUserId).toList ();
        List<UserTokens> userTokens = new ArrayList<> ();
        for (Integer userId : userIds) {
            UserTokens userToken = userTokenService.getUserTokenByIdIfExists (userId);
            if (userToken != null)
                userTokens.add (userToken);
        }
        return userTokens;
    }
    public void sendNotification(int listId) throws ResourceNotFoundException, FirebaseMessagingException {
        List<UserTokens> receivers = getUserTokensForList (listId);
        String message = "someone edited the list 🤔";
        for (UserTokens receiver : receivers) {
            sendNotification (receiver.getToken (),message,"List Edited");
        }
    }

    public void sendNotification(NotificationRequest request) throws Exception {
        List<Integer> newIds = request.getNewShared ().stream ()
                .map (SharedWith::getUserId)
                .collect (Collectors.toList ());
        List<Integer> editIds = request.getEditShared ().stream ()
                .map (SharedWith::getUserId)
                .toList ();
        List<Integer> newAndCanEditIds = new ArrayList<> ();
        List<Integer> canEditListIds = new ArrayList<> ();
        for (Integer editId : editIds) {
            if (newIds.remove (editId)) {
                newAndCanEditIds.add (editId);
            }else {
                canEditListIds.add (editId);
            }
        }
        String title = "List Sharing";
        String username = appUserService.getUser (request.getUserId ()).getUsername ();
        String newMessage = username +  "Has shared a List with you 😬";
        String newAndEditMessage = username + "Has shared a List with you,You can add item to the List! 🫣";
        String canEditMessage = "Now you can Edit "+username +" List! 🤓";
        List<UserTokens> userTokens = userTokenService.getAllUserTokens ();
        List<String> canEditList = userTokens.stream ()
                .filter (userToken -> canEditListIds.contains (userToken.getUserId ()))
                .map (UserTokens::getToken)
                .toList ();
        List<String> newAndCanEditList = userTokens.stream ()
                .filter (userToken -> newAndCanEditIds.contains (userToken.getUserId ()))
                .map (UserTokens::getToken)
                .toList ();
        List<String> newList = userTokens.stream ()
                .filter (userToken -> newIds.contains (userToken.getUserId ()))
                .map (UserTokens::getToken)
                .toList ();
        List<NotificationHolder> recivers = new ArrayList<> ();
        for (String s : canEditList)
            recivers.add (new NotificationHolder (s,canEditMessage));
        for (String s : newAndCanEditList)
            recivers.add (new NotificationHolder (s,newAndEditMessage));
        for (String s : newList)
            recivers.add (new NotificationHolder (s,newMessage));
        for (NotificationHolder receiver : recivers) {
            sendNotification (receiver.getToken (),receiver.getMessage (),title);
        }
    }


}
@Data
@AllArgsConstructor
@NoArgsConstructor
class NotificationHolder{
    private String token,message;
}