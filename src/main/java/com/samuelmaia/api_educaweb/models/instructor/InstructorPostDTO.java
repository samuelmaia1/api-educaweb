package com.samuelmaia.api_educaweb.models.instructor;

import jakarta.validation.constraints.NotNull;

public record InstructorPostDTO(
    @NotNull String name,
    @NotNull String email,
    @NotNull String password
) { }
