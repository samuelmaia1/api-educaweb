package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.response.AuthorizationResponse;
import com.samuelmaia.api_educaweb.models.response.DeleteResponse;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.instructor.*;
import com.samuelmaia.api_educaweb.models.response.LoginResponse;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.register(data));
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao criar conta, por favor tente mais tarde."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDTO loginData){
        try{
            if (instructorService.login(loginData.login(), loginData.password())){
                Instructor instructor = instructorRepository.findByLogin(loginData.login());
                var token = tokenService.generateInstructorToken(instructorRepository.findByLogin(loginData.login()));
                return ResponseEntity.ok(new AuthorizationResponse(token, loginData.login(), instructor.getRole().toString(), instructor.getId()));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Senha inválida.", ""));
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/{instructorId}/course")
    public ResponseEntity<?> addNewCourse(@RequestBody @Validated CourseRequestPost data, @PathVariable String instructorId){
        try{
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

    @DeleteMapping("/{instructorId}/course/{courseId}")
    public ResponseEntity<DeleteResponse> deleteCourse(@PathVariable String instructorId, @PathVariable String courseId){
        return instructorService.deleteCourse(courseId, instructorId);
    }
}
