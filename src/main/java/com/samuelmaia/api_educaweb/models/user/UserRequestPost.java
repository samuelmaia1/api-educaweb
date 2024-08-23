package com.samuelmaia.api_educaweb.models.user;

import org.antlr.v4.runtime.misc.NotNull;

public record UserRequestPost(
        @NotNull String login,
        @NotNull String email,
        @NotNull String password
) { }
