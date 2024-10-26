package com.samuelmaia.api_educaweb.models.instructor;

import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class InstructorGetDTO {
    private String id;
    private String name;
    private String email;
    private List<CourseRequestGet> courses;

    public InstructorGetDTO(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
