package com.samuelmaia.api_educaweb.models.student;

import org.antlr.v4.runtime.misc.NotNull;

public record StudentRequestPost(
        @NotNull String first_name,
        @NotNull String last_name,
        @NotNull String email,
        @NotNull String password,
        @NotNull String login
) {
}
