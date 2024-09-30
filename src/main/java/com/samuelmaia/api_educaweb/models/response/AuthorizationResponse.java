package com.samuelmaia.api_educaweb.models.response;

public record AuthorizationResponse(String token, String userName, String userRole, String userId) {
}
