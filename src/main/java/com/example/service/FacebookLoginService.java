package com.example.service;

import com.example.userComponents.model.AppUser;
import com.example.userComponents.service.AppUserService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.ProfilePictureSource;
import com.restfb.types.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class FacebookLoginService {

    @Value("${facebook.app-id}")
    private String facebookAppId;

    @Value("${facebook.app-secret}")
    private String facebookAppSecret;

    @Value("${facebook.redirect-uri}")
    private String facebookRedirectUri;
    final
    RestTemplate restTemplate = new RestTemplate ();
    final
    AppUserService service;

    public FacebookLoginService(AppUserService service) {
        this.service = service;
    }
    public AppUser callback(String code) {
        FacebookClient.AccessToken accessToken = getAccessToken (code);
        User user = getUserData (accessToken);
        String pictureUrl = "https://graph.facebook.com/v12.0/me/picture?access_token="
                + accessToken.getAccessToken ()
                + "&redirect=false&width=200&height=200";
        ProfilePictureSource pictureSource = Objects.requireNonNullElse (
                        restTemplate.getForObject (pictureUrl, PictureData.class)
                        ,new PictureData ()
                ).getData ();
        user.setPicture (pictureSource);
        AppUser defaultUser = service.getUserByFacebookId (user.getId ());
        if (defaultUser == null) {
            defaultUser = new AppUser ();
            defaultUser.setFacebookId (user.getId ());
            defaultUser.setFname (user.getName ().split (" ")[0]);
            defaultUser.setLname (user.getName ().split (" ")[1]);
            defaultUser.setPictureUrl (user.getPicture ().getUrl ());
            defaultUser.setEmail ("...");
            defaultUser.setGoogleId ("...");
            defaultUser.setUsername ("user" + user.getId ());
            service.saveUser (defaultUser);
            return (defaultUser);
        }
        if (defaultUser.getPictureUrl () != null && !defaultUser.getPictureUrl ().equals (user.getPicture ().getUrl ())) {
            defaultUser.setPictureUrl (user.getPicture ().getUrl ());
            service.saveUser (defaultUser);
        }else if (defaultUser.getPictureUrl () != null && defaultUser.getPictureUrl ().equals (user.getPicture ().getUrl ()))
            return defaultUser;
        else {
            defaultUser.setPictureUrl (user.getPicture ().getUrl ());
            service.saveUser (defaultUser);
        }
        return (defaultUser);
    }

    private FacebookClient.AccessToken getAccessToken(String code) {
        String redirectUri = "http://localhost:8085/login/facebook/callback";
        FacebookClient client = new DefaultFacebookClient (Version.LATEST);
        return client.obtainUserAccessToken (facebookAppId, facebookAppSecret, redirectUri, code);
    }

    private User getUserData(FacebookClient.AccessToken accessToken) {
        FacebookClient client = new DefaultFacebookClient (accessToken.getAccessToken (), Version.LATEST);
        return client.fetchObject ("me", User.class, Parameter.with ("fields", "id,name,email"));
    }

    public String getUrl() {
        return "https://www.facebook.com/v12.0/dialog/oauth?client_id="
                + facebookAppId
                + "&redirect_uri="
                + facebookRedirectUri
                + "/callback&scope=public_profile";
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PictureData {
    ProfilePictureSource data;
}
//    String accessTokenUrl = String.format (
//                "https://graph.facebook.com/v12.0/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s",
//                facebookAppId, redirectUri, facebookAppSecret, code
//        );