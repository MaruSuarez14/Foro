package com.esliceu.Forum.Repos;

import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    List<Topic> findTopicByCategory_id(Long Id);

    @Modifying
    @Query("update Topic set title = :title, content = :content, category = :category, updatedAt = :date where topic_id = :id")
    void updateTopic(String title, String content, Category category, LocalDateTime date, long id);

    @Modifying
    @Query("update Topic set views = :views where topic_id = :id")
    void updateViews(int views, long id);

    @Modifying
    @Query("update Topic set numberOfReplies = :reply where topic_id = :id")
    void updateReplies(int reply, long id);
}
