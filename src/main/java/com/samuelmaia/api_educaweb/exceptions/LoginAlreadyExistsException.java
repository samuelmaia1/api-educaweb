package com.samuelmaia.api_educaweb.exceptions;

public class LoginAlreadyExistsException extends RuntimeException {

    public LoginAlreadyExistsException(){
        super("Nome de usuário já existe.");
    }

    public LoginAlreadyExistsException(String message) {
        super(message);
    }
}
