package com.esliceu.Forum.Forms;

import jakarta.validation.constraints.NotNull;

public class PasswordForm {
    @NotNull
    String currentPassword;

    @NotNull
    String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
