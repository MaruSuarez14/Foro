package com.esliceu.Forum.Forms;

import jakarta.validation.constraints.NotNull;

public class CategoryForm {
    @NotNull
    String title;

    @NotNull
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
