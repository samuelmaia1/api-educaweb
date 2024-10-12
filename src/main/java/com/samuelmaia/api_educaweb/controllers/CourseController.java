package com.samuelmaia.api_educaweb.controllers;


import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorRepository instructorRepository;

    @PostMapping("/register/{instructorId}")
    public ResponseEntity<?> registerNewCourse(@RequestBody @Validated CourseRequestPost data, @PathVariable String instructorId){
        try{
            Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado."));
            Course course = new Course(data, instructor);
            courseRepository.save(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.generateGetDTO(course));
        }
        catch (EntityNotFoundException e){
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseRequestGet>> getAllCourses(@RequestParam(required = false) String category, @RequestParam(required = false) String name){
        List<Course> courses = courseService.findCourses(category, name);
        return ResponseEntity.status(HttpStatus.OK).body(courses.stream().map(course -> courseService.generateGetDTOWithInstructor(course)).toList());
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String  courseId){
        try{
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Curso não existente com este id"));
            return ResponseEntity.status(HttpStatus.OK).body(courseService.generateGetDTOWithInstructor(course));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
