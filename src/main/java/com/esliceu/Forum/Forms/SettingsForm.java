package com.esliceu.Forum.Forms;

import jakarta.validation.constraints.NotNull;

public class SettingsForm {
    @NotNull
    String name;

    @NotNull
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
