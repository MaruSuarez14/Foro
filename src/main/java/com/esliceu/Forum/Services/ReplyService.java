package com.esliceu.Forum.Services;

import com.esliceu.Forum.Model.Reply;
import com.esliceu.Forum.Model.Topic;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Repos.ReplyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyService {
    @Autowired
    ReplyRepo replyRepo;

    @Autowired
    TopicService topicService;

    public Reply createReply(Long topicId, String content, User user) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setUser(user);
        LocalDateTime date = LocalDateTime.now();
        reply.setCreatedAt(date);
        reply.setUpdatedAt(date);
        Topic topic = topicService.getTopicById(topicId);
        reply.setTopic(topic);
        replyRepo.save(reply);
        reply.set_id(reply.getId());
        return reply;
    }

    public List<Reply> getReplyByTopic(Topic topic){
        return replyRepo.findReplyByTopic_id(topic.getId());
    }

    @Transactional
    public void updateReply(String content, Long replyId) {
        replyRepo.updateReply(content, replyId);
    }

    public Reply getReplyById(Long id){
        return replyRepo.findById(id).get();
    }
}
