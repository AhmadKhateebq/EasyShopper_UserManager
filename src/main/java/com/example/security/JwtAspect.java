package com.example.security;

import com.example.annotation.AdminSecured;
import com.example.annotation.EditorSecured;
import com.example.annotation.UserSecured;
import com.example.annotation.ViewerSecured;
import com.example.listComponents.model.UserList;
import com.example.listComponents.service.UserListService;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.util.JWTUtil;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Aspect
@Component
public class JwtAspect {
    private final JWTUtil jwtUtil;
    @Value("${data.admin-key}")
    private String key;
    private static final Logger log = LoggerFactory.getLogger (JwtAspect.class);
    private final UserListService listService;

    @Autowired
    public JwtAspect(JWTUtil jwtUtil, UserListService listService) {
        this.jwtUtil = jwtUtil;
        this.listService = listService;
    }


    @Before(" @annotation(adminSecured)")
    public void validateAdmin(JoinPoint joinPoint, AdminSecured adminSecured) {
        String methodName = joinPoint.getSignature ().getName ();
        log.debug ("Validating admin token for method: {}", methodName);
        String authHeader = getAuthHeader ();
        if (!((authHeader != null) && authHeader.equals ("Bearer " + key)))
            sendError (HttpStatus.UNAUTHORIZED.value (), "invalid ");
    }
    @Before("@annotation(userSecured)")
    public void validateUserId(JoinPoint joinPoint, UserSecured userSecured) {
        Object[] methodArgs = joinPoint.getArgs ();
        String methodName = joinPoint.getSignature ().getName ();
        log.debug ("Validating JWT token for method: {}", methodName);
        String authHeader = getAuthHeader ();
        if (authHeader == null || !authHeader.startsWith ("Bearer ")) {
            sendError (HttpStatus.UNAUTHORIZED.value (), "no token found");
            return;
        }
        try {
            String token = authHeader.substring (7);
            if (!token.equals (key)) {
                if (jwtUtil.extractId (token) != (int) methodArgs[0]) {
                    sendError (HttpStatus.LOCKED.value (), "token id not matched");
                    return;
                }
                if (!jwtUtil.validateToken (token)) {
                    sendError (HttpStatus.UNAUTHORIZED.value (), "invalid access token");
                }
            }
        } catch (NullPointerException e) {
            sendError (HttpStatus.UNAUTHORIZED.value (), "no token found");
        } catch (SignatureException | MalformedJwtException a) {
            sendError (HttpStatus.UNAUTHORIZED.value (), "token invalid");
        }
    }
    @Before("@annotation(editorSecured)")
    public void validateEditor(JoinPoint joinPoint, EditorSecured editorSecured) {
        String methodName = joinPoint.getSignature ().getName ();
        Object[] methodArgs = joinPoint.getArgs ();
        log.debug ("Validating JWT token for method: {}", methodName);
        String authHeader = getAuthHeader ();
        if (authHeader == null || !authHeader.startsWith ("Bearer ")) {
            sendError (HttpStatus.UNAUTHORIZED.value (), "no token found");
            return;
        }
        try {
            String token = authHeader.substring (7);
            if (!token.equals (key)) {
                if (!jwtUtil.validateToken (token)) {
                    sendError (HttpStatus.UNAUTHORIZED.value (), "invalid access token");
                }
                long listId = (long) methodArgs[0];
                UserList userList = getUserListById (listId);
                int userId = jwtUtil.extractId (token);
                if (userList == null || (isListNotSharedWithUser (userList, userId)&&canEdit (userList,userId))) {
                    sendError (HttpStatus.UNAUTHORIZED.value (), "list not shared with you");
                }
            }
        } catch (NullPointerException e) {
            sendError (HttpStatus.UNAUTHORIZED.value (), "no token found");
        } catch (SignatureException | MalformedJwtException a) {
            sendError (HttpStatus.UNAUTHORIZED.value (), "token invalid");
        }
    }
    @Before("@annotation(viewerSecured)")
    public void validateViewer(JoinPoint joinPoint, ViewerSecured viewerSecured) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        log.debug("Validating JWT token for method: {}", methodName);
        String authHeader = getAuthHeader();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(HttpStatus.UNAUTHORIZED.value(), "no token found");
            return;
        }
        try {
            String token = authHeader.substring(7);
            if (!token.equals(key)) {
                if (!jwtUtil.validateToken(token)) {
                    sendError(HttpStatus.UNAUTHORIZED.value(), "invalid access token");
                }
                long listId = (long) methodArgs[0];
                UserList userList = getUserListById(listId);
                int userId = jwtUtil.extractId(token);

                if (userList == null || (userList.getUserId ()!=userId&&isListNotSharedWithUser (userList, userId))) {
                    sendError(HttpStatus.UNAUTHORIZED.value(), "list not shared with you");
                }
            }
        } catch (NullPointerException e) {
            sendError(HttpStatus.UNAUTHORIZED.value(), "no token found");
        } catch (SignatureException | MalformedJwtException a) {
            sendError(HttpStatus.UNAUTHORIZED.value(), "token invalid");
        } catch (ClassCastException e){
            return;
        }
    }

    private Optional<ServletRequestAttributes> getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes ();
            if (attributes instanceof ServletRequestAttributes) {
                return Optional.of ((ServletRequestAttributes) attributes);
            }
        } catch (NullPointerException e) {
            e.printStackTrace ();
        }
        return Optional.empty ();
    }

    private UserList getUserListById(long listId) {
        try {
            return listService.findById (listId);
        } catch (ResourceNotFoundException e) {
            sendError (428, "list not found");
            return null;
        }
    }

    private boolean isListNotSharedWithUser(UserList userList, int userId) {
        return userList.isPrivate () || !userList.sharedWith (userId);
    }
    private boolean canEdit(UserList userList, int userId) {
        return userList.sharedWith (userId);
    }

    private Optional<HttpServletRequest> getRequest() {
        return getRequestAttributes ()
                .map (ServletRequestAttributes::getRequest);
    }

    private Optional<HttpServletResponse> getResponse() {
        return getRequestAttributes ()
                .map (ServletRequestAttributes::getResponse);
    }

    private String getAuthHeader() {
        return getRequest ()
                .map (request -> request.getHeader ("Authorization"))
                .orElse (null);
    }

    private void sendError(int statusCode, String errorMessage) {
        getResponse ().ifPresent (response -> {
            try {
                response.sendError (statusCode, errorMessage);
            } catch (IOException e) {
                throw new RuntimeException (e);
            }
        });
    }
}