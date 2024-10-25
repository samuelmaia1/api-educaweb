package com.samuelmaia.api_educaweb.exceptions;

public class DataIsNotValidException extends RuntimeException{
    public DataIsNotValidException() {
        super("Dados inválidos");
    }

    public DataIsNotValidException(String message) {
        super(message);
    }
}
