package com.samuelmaia.api_educaweb.infra;

import com.samuelmaia.api_educaweb.exceptions.DataIsNotValidException;
import com.samuelmaia.api_educaweb.exceptions.LoginAlreadyExistsException;
import com.samuelmaia.api_educaweb.exceptions.UserNameNotFoundException;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNameNotFoundException.class)
    private ResponseEntity<ErrorResponse> userNameNotFoundHandler(UserNameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(LoginAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> loginAlreaadyExistsHandler(LoginAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage()));
    }

    @ExceptionHandler(DataIsNotValidException.class)
    private ResponseEntity<ErrorResponse> dataIsNotValidHandler(DataIsNotValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }
}
