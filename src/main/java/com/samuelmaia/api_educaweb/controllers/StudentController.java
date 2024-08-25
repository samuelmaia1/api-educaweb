package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.student.Student;
import com.samuelmaia.api_educaweb.models.student.StudentRepository;
import com.samuelmaia.api_educaweb.models.student.StudentRequestPost;
import com.samuelmaia.api_educaweb.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody @Validated StudentRequestPost data){
        try{
            Student student = new Student(data);
            student.setPassword(encoder.encode(student.getPassword()));
            studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(student);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
