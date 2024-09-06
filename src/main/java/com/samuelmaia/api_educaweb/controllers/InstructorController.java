package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.instructor.*;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.course.CourseService;
import com.samuelmaia.api_educaweb.services.instructor.InstructorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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
    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<InstructorGetDTO>> getAllInstructors(){
        try{
            List<Instructor> allInstructors = instructorRepository.findAll();
            List<InstructorGetDTO> allInstructorsDTO = allInstructors.stream()
                    .map(
                        instructor -> instructorService.generateGetDTO(instructor))
                    .toList();
            return ResponseEntity.ok(allInstructorsDTO);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<?> getInstructorById(@PathVariable String instructorId){
        try{
            Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado"));
            return ResponseEntity.status(HttpStatus.OK).body(instructorService.generateGetDTO(instructor));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewInstructor(@RequestBody @Validated InstructorPostDTO data){
        try{
            Instructor instructor = new Instructor(data);
            instructor.setPassword(encoder.encode(instructor.getPassword()));
            instructorRepository.save(instructor);
            return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.generateGetDTO(instructor));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/{instructorId}/course")
    public ResponseEntity<?> addNewCourse(@RequestBody @Validated CourseRequestPost data, @PathVariable String instructorId){
        try{
            //String courseId = instructorService.createCourse(instructorId, data);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(courseService.generateGetDTO(courseRepository.getReferenceById(instructorService.createCourse(instructorId, data))));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), "Chave (name) já existe"));
        }
    }

    @GetMapping("/{instructorId}/course")
    public ResponseEntity<?> getInstructorCourses(@PathVariable String instructorId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(instructorService.getCourses(instructorId));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
