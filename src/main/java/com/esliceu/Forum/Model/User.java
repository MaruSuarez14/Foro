package com.esliceu.Forum.Model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.Map;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long user_id;

    String name;

    String email;

    String password;

    String role;

    String avatarUrl ;

    @Transient
    Map<String, Object> permissions;

    Long _id;

    @OneToMany(mappedBy = "user")
    Set<Topic> topics;

    @OneToMany(mappedBy = "user")
    Set<Reply> replies;

    public Long getId() {
        return user_id;
    }

    public void setId(Long id) {
        this.user_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatar) {
        this.avatarUrl = avatar;
    }

    public Map<String, Object> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Object> permissions) {
        this.permissions = permissions;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
