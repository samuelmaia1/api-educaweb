package com.samuelmaia.api_educaweb.services.instructor;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.course.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    public String createCourse(String instructorId, CourseRequestPost data){
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado"));
        Course course = new Course(data, instructor);
        instructor.getCourses().add(course);
        courseRepository.save(course);
        instructorRepository.save(instructor);
        return course.getId();
    }

    public InstructorGetDTO generateGetDTO(Instructor instructor){
        return new InstructorGetDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getCourses().stream().map(course -> courseService.generateGetDTO(course)).toList()
        );
    }

    public List<CourseRequestGet> getCourses(String instructorId){
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado"));
        return instructor.getCourses().stream().map(course -> courseService.generateGetDTO(course)).toList();
    }
}
