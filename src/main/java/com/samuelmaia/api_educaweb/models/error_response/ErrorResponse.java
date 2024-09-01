package com.samuelmaia.api_educaweb.models.error_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private int statusCode;
    private String details;
    private String message;

    public ErrorResponse(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
