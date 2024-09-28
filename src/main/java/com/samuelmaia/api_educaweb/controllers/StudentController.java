package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.response.AuthorizationResponse;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.response.LoginResponse;
import com.samuelmaia.api_educaweb.models.student.*;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.repositories.StudentRepository;
import com.samuelmaia.api_educaweb.services.TokenService;
import com.samuelmaia.api_educaweb.services.student.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

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
        try{
            if (studentService.login(loginData.login(), loginData.password())){
                Student student = studentRepository.findByLogin(loginData.login());
                var token = tokenService.generateStudentToken(studentRepository.findByLogin(loginData.login()));
                return ResponseEntity.ok(new AuthorizationResponse(token, loginData.login(), student.getRole().toString()));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Senha inválidos.", ""));
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Usuário não encontrado."));
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId){
        try{
            studentService.deleteStudent(studentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentRequestGet> getStudent(@PathVariable String studentId){
        System.out.println(studentId);
        try{
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado."));
            StudentRequestGet studentRequestGet = studentService.generateStudentGetDTO(student);
            return ResponseEntity.ok(studentRequestGet);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody @Validated StudentRequestPost data){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.register(data));
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getAllFinishedCourses(@PathVariable String studentId){
        try{
            List<CourseRequestGet> courseList = studentService.getFinishedCourses(studentId);
            return ResponseEntity.status(HttpStatus.OK).body(courseList);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
    
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addFinishedCourse(@PathVariable String studentId, @PathVariable String courseId){
        try{
            studentService.addFinishedCourse(studentId, courseId);
            return ResponseEntity.ok(studentService.generateStudentGetDTO(studentRepository.getReferenceById(studentId)));
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
        try{
            Student updatedStudent = studentService.updateStudent(studentId, data);
            studentRepository.save(updatedStudent);
            return ResponseEntity.ok(updatedStudent);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno"  ));
        }
    }

}
