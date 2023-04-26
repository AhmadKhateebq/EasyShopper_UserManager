package com.example.usermanager.security;

import com.example.usermanager.annotation.JwtSecured;
import com.example.usermanager.exceptions.UnauthorizedException;
import com.example.usermanager.util.JWTUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class JwtAspect {
    @Autowired
    private JWTUtil jwtUtil;
    @Before("execution(* com.example.usermanager.controller.*.*(..)) && @annotation(jwtSecured)")
    public void validateJwt(JoinPoint joinPoint, JwtSecured jwtSecured) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes ();
        HttpServletRequest request = attributes.getRequest ();

        String authHeader = request.getHeader ("Authorization");
        if (authHeader == null || !authHeader.startsWith ("Bearer ")) {
            throw new UnauthorizedException ("Missing or invalid Authorization header");
        }

        String token = authHeader.substring (7);

        if (!jwtUtil.validateToken (token)) {
            throw new UnauthorizedException ("Invalid JWT token");
        }
    }
}
