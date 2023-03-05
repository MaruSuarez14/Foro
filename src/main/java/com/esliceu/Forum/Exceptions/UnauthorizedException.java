package com.esliceu.Forum.Exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super("Incorrect email or password.");
    }
}
