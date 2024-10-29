package com.samuelmaia.api_educaweb.services;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import com.samuelmaia.api_educaweb.models.student.Student;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> findCourses(String category, String name){
        if (category != null && name != null) return courseRepository.findByCategoryAndName(category, name);
        else if (category != null) return courseRepository.findByCategory(category);
        else if (name != null) return courseRepository.findByName(name);
        else return courseRepository.findAll();
    }

    public void removeStudent(Course course, Student student){
        course.getStudents().remove(student);
        courseRepository.save(course);
    }

    public InstructorGetDTOByCourse generateInstructorDTO(Instructor instructor){
        return new InstructorGetDTOByCourse(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail()
        );
    }
}