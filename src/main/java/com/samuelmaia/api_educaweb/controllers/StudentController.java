package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.response.AuthorizationResponse;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.response.LoginResponse;
import com.samuelmaia.api_educaweb.models.student.*;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.repositories.StudentRepository;
import com.samuelmaia.api_educaweb.services.DTOService;
import com.samuelmaia.api_educaweb.services.TokenService;
import com.samuelmaia.api_educaweb.services.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    DTOService dtoService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok().body(studentRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDTO loginData){
        if (studentService.login(loginData.login(), loginData.password())){
            Student student = studentRepository.findByLogin(loginData.login());
            var token = tokenService.generateStudentToken(student);
            return ResponseEntity.ok(new AuthorizationResponse(token, loginData.login(), student.getRole().toString(), student.getId()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Senha inválidos.", ""));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId){
        studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentRequestGet> getStudent(@PathVariable String studentId){
        StudentRequestGet student = studentService.getStudent(studentId);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody @Validated StudentRequestPost data){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.register(data));
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getAllFinishedCourses(@PathVariable String studentId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getFinishedCourses(studentId));
    }
    
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addFinishedCourse(@PathVariable String studentId, @PathVariable String courseId){
        try{
            studentService.addFinishedCourse(studentId, courseId);
            return ResponseEntity.ok(dtoService.student(studentRepository.getReferenceById(studentId)));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }


    @GetMapping("/{studentId}/vacancies")
    public ResponseEntity<List<Vacancy>> getAllVacancies(@PathVariable String studentId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllVacancies(studentId));
    }

    @PostMapping("/{studentId}/vacancies/{vacancyId}")
    public ResponseEntity<Student> addVacancy(@PathVariable String studentId, @PathVariable String vacancyId){
        studentService.addVacancy(studentId, vacancyId);
        return ResponseEntity.ok(studentRepository.getReferenceById(studentId));
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@Parameter(description = "id do usuário a ser atualizado") @PathVariable String studentId, @RequestBody @Validated StudentRequestPut data){
        return ResponseEntity.ok(studentService.updateStudent(studentId, data));
    }

}
