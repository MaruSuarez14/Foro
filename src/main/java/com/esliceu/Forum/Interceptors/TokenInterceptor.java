package com.esliceu.Forum.Interceptors;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Services.TokenService;
import com.esliceu.Forum.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        try {
            String token = authHeader.replace("Bearer ", "");
            String userMail = tokenService.getUser(token);
            User user = userService.getProfile(userMail);
            request.setAttribute("user", user);
            return true;

        } catch (SignatureVerificationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
