package com.samuelmaia.api_educaweb.models.student;

import jakarta.validation.constraints.NotNull;

public record StudentRequestPut(
      String password,
      String email,
      String login,
      String firstName,
      String lastName
) { }
