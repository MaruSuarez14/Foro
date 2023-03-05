package com.esliceu.Forum.Exceptions;

public class PasswordException extends Exception {
    int statusCode;
    public PasswordException(String message,int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
