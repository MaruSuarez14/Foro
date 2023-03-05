package com.esliceu.Forum.Repos;

import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    List<Topic> findTopicByCategory_id(Long Id);

    @Modifying
    @Query("update Topic set title = :title, content = :content, category = :category where topic_id = :id")
    void updateTopic(String title, String content, Category category, long id);
}
