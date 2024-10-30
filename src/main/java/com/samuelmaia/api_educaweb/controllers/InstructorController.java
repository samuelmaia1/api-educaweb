package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.response.AuthorizationResponse;
import com.samuelmaia.api_educaweb.models.response.DeleteResponse;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.instructor.*;
import com.samuelmaia.api_educaweb.models.response.LoginResponse;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.DTOService;
import com.samuelmaia.api_educaweb.services.TokenService;
import com.samuelmaia.api_educaweb.services.CourseService;
import com.samuelmaia.api_educaweb.services.InstructorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    DTOService dtoService;

    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorService instructorService;
    @Autowired
    CourseService courseService;
    @Autowired
    TokenService tokenService;


    @GetMapping
    public ResponseEntity<List<InstructorGetDTO>> getAllInstructors(){
        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<InstructorGetDTO> getInstructorById(@PathVariable String instructorId){
        return ResponseEntity.ok(instructorService.getInstructorById(instructorId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewInstructor(@RequestBody @Validated InstructorPostDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.register(data));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDTO loginData){
            if (instructorService.login(loginData.login(), loginData.password())){
                Instructor instructor = instructorRepository.findByLogin(loginData.login());
                var token = tokenService.generateInstructorToken(instructor);
                return ResponseEntity.ok(new AuthorizationResponse(token, loginData.login(), instructor.getRole().toString(), instructor.getId()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Senha inválida.", ""));
    }

    @PostMapping("/{instructorId}/course")
    public ResponseEntity<?> addNewCourse(@RequestBody @Validated CourseRequestPost data, @PathVariable String instructorId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoService.course(courseRepository.getReferenceById(instructorService.createCourse(instructorId, data))));
    }

    @GetMapping("/{instructorId}/course")
    public ResponseEntity<?> getInstructorCourses(@PathVariable String instructorId){
        return ResponseEntity.status(HttpStatus.OK).body(instructorService.getCourses(instructorId));
    }

    @DeleteMapping("/{instructorId}/course/{courseId}")
    public ResponseEntity<DeleteResponse> deleteCourse(@PathVariable String instructorId, @PathVariable String courseId){
        return instructorService.deleteCourse(courseId, instructorId);
    }
}
