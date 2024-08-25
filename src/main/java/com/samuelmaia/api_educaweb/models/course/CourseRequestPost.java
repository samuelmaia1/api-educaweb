package com.samuelmaia.api_educaweb.models.course;

import org.antlr.v4.runtime.misc.NotNull;

public record CourseRequestPost(
        @NotNull String name,
        @NotNull String category
) { }
