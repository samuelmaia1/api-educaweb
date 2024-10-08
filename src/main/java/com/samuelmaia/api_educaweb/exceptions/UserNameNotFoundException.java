package com.samuelmaia.api_educaweb.exceptions;

public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException() {
        super("Login não encontrado.");
    }

    public UserNameNotFoundException(String message) {
        super(message);
    }
}
