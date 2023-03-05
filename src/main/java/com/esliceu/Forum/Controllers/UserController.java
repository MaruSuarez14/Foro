package com.esliceu.Forum.Controllers;

import com.esliceu.Forum.Exceptions.UnauthorizedException;
import com.esliceu.Forum.Forms.Credentials;
import com.esliceu.Forum.Forms.UserForm;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Services.CategoryService;
import com.esliceu.Forum.Services.TokenService;
import com.esliceu.Forum.Services.UserService;
import com.esliceu.Forum.Utils.EncodePassword;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    TokenService tokenService;
    UserService userService;
    CategoryService categoryService;

    public UserController(TokenService tokenService, UserService userService, CategoryService categoryService){
        this.tokenService = tokenService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @PostMapping("/register")
    @CrossOrigin
    public Map<String, Object> signUp (@Valid @RequestBody UserForm body, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        List<User> userList = userService.getUserByMail(
                body.getEmail());
        if (!userList.isEmpty()) {
            response.setStatus(400);
            map.put("message", "This user already exists");
            return map;
        }
        User user = new User();
        user.setName(body.getName());
        user.setPassword(EncodePassword.encode(body.getPassword()));
        user.setEmail(body.getEmail());
        user.setRole(body.getRole());
        userService.createUser(user);
        response.setStatus(200);
        map.put("message", "done");
        return map;
    }

    @PostMapping("/login")
    @CrossOrigin
    public Map<String, Object> login (@RequestBody Credentials credentials, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        User user = null;
        try {
            user = userService.logIn(credentials);
            user = userService.completeUser(user);
            response.setStatus(200);
        } catch (UnauthorizedException e) {
            map.put("message", e.getMessage());
            response.setStatus(401);
            return map;
        }
        String token = tokenService.newToken(user.getEmail());
        map.put("token", token);
        map.put("user", user);
        return map;
    }


    @GetMapping("/getprofile")
    @CrossOrigin(origins = "http://localhost:3000")
    public User getProfile(HttpServletRequest request, HttpServletResponse response){
       try {
            User user = (User) request.getAttribute("user");
            user = userService.completeUser(user);
            if (user != null){
                response.setStatus(200);
                return user;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

}
