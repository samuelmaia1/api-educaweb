package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.error_response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.instructor.InstructorPostDTO;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.instructor.InstructorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<InstructorGetDTO>> getAllInstructors(){
        try{
            List<Instructor> allInstructors = instructorRepository.findAll();
            List<InstructorGetDTO> allInstructorsDTO = allInstructors.stream()
                    .map(
                        instructor -> new InstructorGetDTO(
                        instructor.getId(),
                        instructor.getName(),
                        instructor.getEmail(),
                        instructor.getCourses().stream().map(course -> new CourseRequestGet(course.getId(), course.getName(), course.getCategory(), course.getDescription(), course.getUrl())).toList()))
                    .toList();
            return ResponseEntity.ok(allInstructorsDTO);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewInstructor(@RequestBody @Validated InstructorPostDTO data){
        try{
            Instructor instructor = new Instructor(data);
            instructor.setPassword(encoder.encode(instructor.getPassword()));
            instructorRepository.save(instructor);
            return ResponseEntity.status(HttpStatus.CREATED).body(instructor);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/{instructorId}/course")
    public ResponseEntity<?> addNewCourse(@RequestBody @Validated CourseRequestPost data, @PathVariable String instructorId){
        try{
            String courseId = instructorService.createCourse(instructorId, data);
            Course course = courseRepository.getReferenceById(courseId);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new CourseRequestGet(
                    course.getId(),
                    course.getName(),
                    course.getCategory(),
                    course.getDescription(),
                    course.getUrl())
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
