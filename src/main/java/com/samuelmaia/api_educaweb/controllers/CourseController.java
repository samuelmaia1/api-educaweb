package com.samuelmaia.api_educaweb.controllers;


import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRepository;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/register")
    public ResponseEntity<Course> registerNewCourse(@RequestBody @Validated CourseRequestPost data){
        try{
            Course course = new Course(data);
            courseRepository.save(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        }
        catch (Exception e){
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
