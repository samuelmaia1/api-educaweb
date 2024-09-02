package com.samuelmaia.api_educaweb.models.instructor;

import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;

import java.util.List;

public record InstructorGetDTO(
        String id,
        String name,
        String email,
        List<CourseRequestGet> courses
) {
}
