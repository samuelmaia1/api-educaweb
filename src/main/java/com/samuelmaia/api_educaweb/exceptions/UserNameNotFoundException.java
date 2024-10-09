package com.samuelmaia.api_educaweb.exceptions;

public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException() {
        super("Usuário não encontrado.");
    }

    public UserNameNotFoundException(String message) {
        super(message);
    }
}