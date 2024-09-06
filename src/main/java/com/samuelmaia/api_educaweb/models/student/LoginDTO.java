package com.samuelmaia.api_educaweb.models.student;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull String login,
        @NotNull String password
) {
}
