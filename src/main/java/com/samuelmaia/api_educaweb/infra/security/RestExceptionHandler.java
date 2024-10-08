package com.samuelmaia.api_educaweb.infra.security;

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
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }
}
