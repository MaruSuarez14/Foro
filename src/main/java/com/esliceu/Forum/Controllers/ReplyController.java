package com.esliceu.Forum.Controllers;

import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Model.Reply;
import com.esliceu.Forum.Model.User;
import com.esliceu.Forum.Services.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class ReplyController {
    ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/topics/{topicId}/replies")
    @CrossOrigin(origins = "http://localhost:3000")
    public Reply createReply(@PathVariable Long topicId, @RequestBody Map<String, String> body, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        String content = body.get("content");
        Reply reply = replyService.createReply(topicId, content, user);
        if(reply!=null){
            return reply;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("topics/{topicId}/replies/{replyId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Reply updateReply(@RequestBody Map<String, String> body, @PathVariable Long replyId){
        String content = body.get("content");
        replyService.updateReply(content, replyId);
        return replyService.getReplyById(replyId);
    }
}
