package com.samuelmaia.api_educaweb.exceptions;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(){
        super("Senha inválida");
    }

    public InvalidPasswordException(String message){
        super(message);
    }
}
