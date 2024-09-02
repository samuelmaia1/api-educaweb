package com.samuelmaia.api_educaweb.models.course;

import jakarta.validation.constraints.NotNull;

public record CourseRequestPost(
        @NotNull String name,
        @NotNull String category,
        @NotNull String description,
        @NotNull String url
) { }
