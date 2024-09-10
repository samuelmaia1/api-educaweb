package com.samuelmaia.api_educaweb.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    STUDENT("student"),
    INSTRUCTOR("instructor"),
    COMPANY("company");

    private final String role;
}
