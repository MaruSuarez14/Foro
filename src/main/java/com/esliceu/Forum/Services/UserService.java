package com.esliceu.Forum.Services;

import com.esliceu.Forum.Exceptions.UnauthorizedException;
import com.esliceu.Forum.Forms.Credentials;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Repos.UserRepo;
import com.esliceu.Forum.Utils.EncodePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryService categoryService;

    public User  logIn(Credentials credentials) throws UnauthorizedException {
        List<User> users = userRepo.findAll();
        for (User u: users) {
            if(u.getEmail().equals(credentials.getEmail()) && u.getPassword().equals(EncodePassword.encode(
                    credentials.getPassword()))) {
                return u;
            }
        }
        throw new UnauthorizedException();
    }

    public List<User> getUserByMail(String mail) {
        return userRepo.findByEmail(mail);
    }

    public User getProfile(String email) {
        List<User> users = userRepo.findByEmail(email);
        if (users.isEmpty()){
            return null;
        }
        return completeUser(users.get(0));
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    public User completeUser (User user){
        user.setAvatarUrl("");
        user.set_id(user.getId());
        user.set_id(user.getId());
        user.setAvatarUrl("");
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("categories", categoryService.getAllCategories());
        permissions.put("root", setPermissions(user.getRole()));
        user.setPermissions(permissions);
        return user;
    }

    public String [] setPermissions(String role) {
        String[] result = {};
        switch(role) {
            case "admin":
                result = new String[]{
                        "own_topics:write",
                        "own_topics:delete",
                        "own_replies:write",
                        "own_replies:delete",
                        "categories:write",
                        "categories:delete"
                };
                break;
        }
        return result;
    }



}
