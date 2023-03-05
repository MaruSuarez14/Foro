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
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepo topicRepo;


    @Autowired
    ReplyRepo replyRepo;


    public List<Topic> getTopicsByCategory(Category category) {
        return topicRepo.findTopicByCategory_id(category.getId());
    }

    public Topic createTopic(TopicForm body, User user, Category category) {
        Topic topic = new Topic();
        topic.setTitle(body.getTitle());
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
        return topicRepo.findById(id).get();
    }

    @Transactional
    public void update(String content, String title, long id, Category category) {
        LocalDateTime date = LocalDateTime.now();
        topicRepo.updateTopic(title, content, category, date, id);
    }

    public void deleteTopic(Long topicId) {
        List<Reply> replies = replyRepo.findReplyByTopic_id(topicId);
        if(!replies.isEmpty()) {
            for (Reply r : replies) {
                replyRepo.deleteById(r.getId());
            }
        }
        topicRepo.deleteById(topicId);
    }
}
