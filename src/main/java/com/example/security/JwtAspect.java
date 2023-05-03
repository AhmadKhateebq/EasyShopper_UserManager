package com.example.security;

import com.example.annotation.AdminSecured;
import com.example.annotation.JwtSecured;
import com.example.util.JWTUtil;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class JwtAspect {
    @Autowired
    private JWTUtil jwtUtil;
    @Value("${data.admin-key}")
    private String key;
    private static final Logger log = LoggerFactory.getLogger (JwtAspect.class);

    @Before(("(execution(* com.example.userComponents.controller.*.*(..)) " +
            "|| execution(* com.example.itemComponents.controller.*.*(..)) )") +
            "&& @annotation(jwtSecured)")
//    @Before("@annotation(jwtSecured)")
    public void validateJwt(JoinPoint joinPoint, JwtSecured jwtSecured) throws Throwable {
        String methodName = joinPoint.getSignature ().getName ();
        log.debug ("Validating JWT token for method: {}", methodName);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes ();
        HttpServletRequest request = attributes.getRequest ();
        HttpServletResponse response = attributes.getResponse ();
        String authHeader = request.getHeader ("Authorization");
        if (authHeader == null || !authHeader.startsWith ("Bearer ")) {
            response.sendError (HttpStatus.UNAUTHORIZED.value (), "no token found");
        }
        try {
            String token = authHeader.substring (7);

            if (!jwtUtil.validateToken (token)) {
                response.sendError (HttpStatus.UNAUTHORIZED.value (), "invalid access token");
            }
        } catch (NullPointerException e) {
            response.sendError (HttpStatus.UNAUTHORIZED.value (), "no token found");
        } catch (SignatureException a) {
            response.sendError (HttpStatus.UNAUTHORIZED.value (), "token invalid");
        }
    }

    @Before(("(execution(* com.example.userComponents.controller.*.*(..)) " +
            "|| execution(* com.example.itemComponents.controller.*.*(..))) " )+
            "&& @annotation(adminSecured)"
    )
//    @Before(" @annotation(adminSecured)")
    public void validateAdmin(JoinPoint joinPoint, AdminSecured adminSecured) throws Throwable {
        String methodName = joinPoint.getSignature ().getName ();
        log.debug ("Validating admin token for method: {}", methodName);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes ();
        HttpServletRequest request = attributes.getRequest ();
        HttpServletResponse response = attributes.getResponse ();
        String authHeader = request.getHeader ("Authorization");
        if (!((authHeader != null) && authHeader.equals ("Bearer " + key)))
            response.sendError (HttpStatus.UNAUTHORIZED.value (), "invalid ");
    }
}
/*
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhaG1hZGtoYXRlZWJxIiwiaWF0IjoxNjgzMDY1MDQ0fQ.VIoarut9YjP9-DjJPAfT__AkbrtYlTlqeF0w2aW4yeJoNNxiu182LmHFLYEzjYzcsVkGde5HpT9bJQuZY7M-IA
*/