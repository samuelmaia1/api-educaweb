package com.samuelmaia.api_educaweb.services.course;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import com.samuelmaia.api_educaweb.services.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public CourseRequestGet generateGetDTO(Course course){
        return new CourseRequestGet(course.getId(), course.getName(), course.getCategory(), course.getDescription(), course.getUrl());
    }

    public CourseRequestGet generateGetDTOWithInstructor(Course course){
        return new CourseRequestGet(course.getId(), course.getName(), course.getCategory(), course.getDescription(), course.getUrl(), new InstructorGetDTOByCourse(
                course.getInstructor().getId(),
                course.getInstructor().getName(),
                course.getInstructor().getEmail()
        ));
    }
}
