package com.esliceu.Forum.Repos;

import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    @Modifying
    @Query("update User set name = :name, email = :email where user_id = :id")
    void updateUserProfile(String name, String email, long id);
}
