package com.esliceu.Forum.Forms;

import jakarta.validation.constraints.NotNull;

public class TopicForm {
    @NotNull
    String title;

    @NotNull
    String content;

    @NotNull
    String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
