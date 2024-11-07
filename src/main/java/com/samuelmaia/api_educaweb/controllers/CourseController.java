package com.samuelmaia.api_educaweb.controllers;


import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.CourseService;
import com.samuelmaia.api_educaweb.services.DTOService;
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
    DTOService dtoService;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorRepository instructorRepository;

    @PostMapping("/register/{instructorId}")
    public ResponseEntity<?> registerNewCourse(@RequestBody @Validated CourseRequestPost data, @PathVariable String instructorId){
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoService.course(courseService.registerNewCourse(data, instructorId)));
    }

    @GetMapping
    public ResponseEntity<List<CourseRequestGet>> getAllCourses(@RequestParam(required = false) String category, @RequestParam(required = false) String name){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.findCourses(category, name).stream().map(course -> dtoService.course(course)).toList());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable String  courseId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoService.course(courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Curso não existente com este id"))));
    }
}
