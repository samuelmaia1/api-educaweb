package com.samuelmaia.api_educaweb.models.instructor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InstructorGetDTOByCourse {
    private String id;
    private String name;
    private String email;
}
