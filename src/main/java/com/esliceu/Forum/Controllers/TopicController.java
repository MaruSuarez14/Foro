package com.esliceu.Forum.Controllers;

import com.esliceu.Forum.Forms.CategoryForm;
import com.esliceu.Forum.Forms.TopicForm;
import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Model.Reply;
import com.esliceu.Forum.Model.Topic;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Services.ReplyService;
import com.esliceu.Forum.Services.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    TopicService topicService;
    ReplyService replyService;


    public TopicController(TopicService topicService, ReplyService replyService) {
        this.topicService = topicService;
        this.replyService = replyService;
    }

    @GetMapping("/categories/{slug}/topics")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Topic> getTopics(@PathVariable String slug, HttpServletResponse response) {
        List<Topic> topics = topicService.getTopicsByCategory(slug);
        if(topics!=null) {
            return topics;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/topics")
    @CrossOrigin(origins = "http://localhost:3000")
    public Topic createTopic(@Valid @RequestBody TopicForm body, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Topic topic = topicService.createTopic(body, user);
        if(topic!=null) {
            return topic;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Map<String, Object> getTopic(@PathVariable Long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        Map<String, Object> map = new HashMap<>();
        if(topic!=null){
            map.put("category", topic.getCategory());
            map.put("content", topic.getContent());
            map.put("id", topic.getId());
            map.put("numberOfReplies", null);
            map.put("title", topic.getTitle());
            map.put("createdAt", topic.getCreatedAt());
            map.put("updatedAt", topic.getUpdatedAt());
            map.put("views", topic.getViews());
            map.put("__v", 0);
            map.put("_id", topic.getId());
            map.put("user",topic.getUser());
            List<Reply> replies = replyService.getReplyByTopic(topic);
            map.put("replies", replies);
        }
        return map;

    }

    @PutMapping("/topics/{topicId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Topic updateTopic(@Valid @RequestBody TopicForm body, @PathVariable long topicId) {
        topicService.update(body.getCategory(), body.getContent(), body.getTitle(), topicId);
        return topicService.getTopicById(topicId);
    }
}