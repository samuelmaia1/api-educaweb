package com.samuelmaia.api_educaweb.services.course;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public CourseRequestGet generateGetDTO(Course course){
        return new CourseRequestGet(course.getId(), course.getName(), course.getCategory(), course.getDescription(), course.getUrl());
    }

    public CourseRequestGet generateGetDTOWithInstructor(Course course){
        return new CourseRequestGet(course.getId(), course.getName(), course.getCategory(), course.getDescription(), course.getUrl());
    }

    public List<Course> findCourses(String category, String name){
        if (category != null && name != null) return courseRepository.findByCategoryAndName(category, name);
        else if (category != null) return courseRepository.findByCategory(category);
        else if (name != null) return courseRepository.findByName(name);
        else return courseRepository.findAll();
    }
}
