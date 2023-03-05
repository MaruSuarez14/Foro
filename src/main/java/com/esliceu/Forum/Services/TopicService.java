package com.esliceu.Forum.Services;

import com.esliceu.Forum.Forms.TopicForm;
import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Model.Reply;
import com.esliceu.Forum.Model.Topic;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Repos.ReplyRepo;
import com.esliceu.Forum.Repos.TopicRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    ReplyRepo replyRepo;

    public List<Topic> getTopicsByCategory(String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        List<Topic> topics = topicRepo.findTopicByCategory_id(category.getId());
        if(!topics.isEmpty()) {
            for (Topic t: topics) {
                t.set_id(t.getId());
                t.setUser(userService.completeUser(t.getUser()));
            }
        }
        return topics;
    }

    public Topic createTopic(TopicForm body, User user) {
        Topic topic = new Topic();
        topic.setTitle(body.getTitle());
        Category category = categoryService.getCategoryBySlug(body.getCategory());
        topic.setCategory(category);
        topic.setContent(body.getContent());
        LocalDateTime date = LocalDateTime.now();
        topic.setCreatedAt(date);
        topic.setUpdatedAt(date);
        topic.setViews(0);
        topic.setUser(user);
        topicRepo.save(topic);
        topic.set_id(topic.getId());
        return topic;
    }

    public Topic getTopicById(Long id) {
        Topic topic = topicRepo.findById(id).get();
        topic.setUser(userService.completeUser(topic.getUser()));
        return topic;
    }

    @Transactional
    public void update(String categorySlug, String content, String title, long id) {
        Category category = categoryService.getCategoryBySlug(categorySlug);
        topicRepo.updateTopic(title, content, category, id);
    }
}
