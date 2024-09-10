package com.samuelmaia.api_educaweb.models.response;

import jakarta.validation.constraints.NotNull;

public record DeleteResponse(
        @NotNull Boolean deleted,
        @NotNull String message
) {
}
