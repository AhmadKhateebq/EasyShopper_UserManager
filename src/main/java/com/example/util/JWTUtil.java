package com.example.util;

import com.example.userComponents.model.AppUser;
import com.example.userComponents.service.AppUserService;
import com.example.userComponents.service.PasswordService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    @Value("${data.key}")
    private String SECRET_KEY;
    final
    AppUserService userService;
    final
    PasswordService passwordService;
    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
    @Autowired
    public JWTUtil(AppUserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    public String getToken(String username, String password) {
        AppUser user = userService.getUserByUsername (username);
        try {
            if (user.getUsername ().equals (username) &&
                    passwordEncoder.matches (password, passwordService.getUser (user.getId ()).getPassword ()))
                return generateToken (username, user.getId ());
            else return null;
        } catch (Exception e) {
            throw new RuntimeException ("user not found");
        }
    }

    public boolean validate(String username, String password, String token) {
        AppUser user = userService.getUserByUsername (username);
        try {
            if (user.getUsername ().equals (username) && passwordService.getUser (user.getId ()).getPassword ().equals (password))
                return validateToken (token);
        } catch (Exception e) {
            throw new RuntimeException ("token is invalid or user not found");
        }
        return false;
    }

    public String generateToken(String username, int user_id) {
        Map<String, Object> claims = new HashMap<> ();
        return createToken (claims, username, user_id);
    }

    private String createToken(Map<String, Object> claims, String subject, int user_id) {
        JwtBuilder jwts = Jwts.builder ();
        jwts.setClaims (claims);
        jwts.setSubject (subject);
        jwts.setIssuedAt (new Date (System.currentTimeMillis ()));
        jwts.setId (String.valueOf (user_id));
        jwts.signWith (SignatureAlgorithm.HS512, SECRET_KEY);
        return jwts.compact ();
    }

    public String extractSubject(String token) {
        return extractClaim (token, Claims::getSubject);
    }

    public int extractId(String token) {
        System.out.println (extractClaim (token,Claims::getId));
        return Integer.parseInt (extractClaim (token, Claims::getId));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims (token);
        return claimsResolver.apply (claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser ().setSigningKey (SECRET_KEY).parseClaimsJws (token).getBody ();
    }

    public boolean validateToken(String token) {
        String username = extractSubject (token);
        int userId = extractId (token);
        AppUser user = userService.getUserByUsername (username);
        return user != null && user.getId () == userId;
    }
}