package com.esliceu.Forum.Repos;

import com.esliceu.Forum.Model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    List<Reply> findReplyByTopic_id(Long id);

    @Modifying
    @Query("update Reply set content = :content, updatedAt = :date where reply_id = :id")
    void updateReply(String content, Long id, LocalDateTime date);
}
