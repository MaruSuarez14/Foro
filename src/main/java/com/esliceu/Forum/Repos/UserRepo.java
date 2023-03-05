package com.esliceu.Forum.Repos;

import com.esliceu.Forum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
