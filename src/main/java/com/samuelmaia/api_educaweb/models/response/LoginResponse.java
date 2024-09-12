package com.samuelmaia.api_educaweb.models.response;

import jakarta.validation.constraints.NotNull;

public record LoginResponse(
        @NotNull Boolean ok,
        @NotNull String message,
        String token
) {
}
