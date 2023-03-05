package com.esliceu.Forum.Services;

import com.esliceu.Forum.Exceptions.PasswordException;
import com.esliceu.Forum.Exceptions.UnauthorizedException;
import com.esliceu.Forum.Forms.Credentials;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Repos.UserRepo;
import com.esliceu.Forum.Utils.EncodePassword;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public User updateProfile (String name, String email) {
        User user = getProfile(email);
        userRepo.updateUserProfile(name, email, user.getId());
        entityManager.refresh(user);
        return getProfile(email);
    }

    @Transactional
    public boolean updatePassword(String currentPassword, String newPassword, User user) throws PasswordException {
        String encodeCurrentPassword = EncodePassword.encode(currentPassword);
        String encodeNewPassword = EncodePassword.encode(newPassword);
        if(!encodeCurrentPassword.equals(user.getPassword())) {
            throw new PasswordException("Your current password is wrong!", 401);
        }

        if(encodeCurrentPassword.equals(encodeNewPassword)){
            throw new PasswordException("Your new password cannot be the same as the old password", 400);
        }
        if(encodeCurrentPassword.equals(user.getPassword())) {
            userRepo.updateUserPassword(encodeNewPassword, user.getId());
            return true;
        }

        return false;

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
        //switch(role) {
            //case "admin":
                result = new String[]{
                        "own_topics:write",
                        "own_topics:delete",
                        "own_replies:write",
                        "own_replies:delete",
                        "categories:write",
                        "categories:delete"
                };
               // break;
        //}
        return result;
    }



}
