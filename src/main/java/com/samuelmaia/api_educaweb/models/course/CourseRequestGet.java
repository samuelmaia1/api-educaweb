package com.samuelmaia.api_educaweb.models.course;

import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CourseRequestGet {
    private String id;
    private String name;
    private String category;
    private String description;
    private String url;
    private InstructorGetDTO instructor;
}
