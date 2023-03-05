package com.esliceu.Forum.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reply_id;

    String content;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="topic_id")
    Topic topic;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    Long _id;

    public Long getId() {
        return reply_id;
    }

    public void setId(Long id) {
        this.reply_id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
