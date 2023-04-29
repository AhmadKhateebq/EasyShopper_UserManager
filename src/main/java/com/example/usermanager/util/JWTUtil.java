package com.example.usermanager.util;

import com.example.usermanager.model.AppUser;
import com.example.usermanager.service.AppUserService;
import com.example.usermanager.service.PasswordService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    @Value("${data.key}")
    private String SECRET_KEY;
    @Autowired
    AppUserService userService;
    @Autowired
    PasswordService passwordService;

    public String getToken(String username, String password) {
        AppUser user = userService.getUserByUsername (username);
        try {
            if (user.getUsername ().equals (username) && passwordService.getUser (user.getId ()).getPassword ().equals (password))
                return generateToken (username);
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

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<> ();
        return createToken (claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        JwtBuilder jwts = Jwts.builder ();
        jwts.setClaims (claims);
        jwts.setSubject (subject);
        jwts.setIssuedAt (new Date (System.currentTimeMillis ()));
        jwts.signWith (SignatureAlgorithm.HS512, SECRET_KEY);
        return jwts.compact ();
    }

    public String extractUsername(String token) {
        return extractClaim (token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims (token);
        return claimsResolver.apply (claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser ().setSigningKey (SECRET_KEY).parseClaimsJws (token).getBody ();
    }

    public boolean validateToken(String token) {
        final String username = extractUsername (token);

        return (userService.userNameExists (username));
    }
}
