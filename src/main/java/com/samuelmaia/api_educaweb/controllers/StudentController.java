package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.student.Student;
import com.samuelmaia.api_educaweb.models.student.StudentRepository;
import com.samuelmaia.api_educaweb.models.student.StudentRequestGet;
import com.samuelmaia.api_educaweb.models.student.StudentRequestPost;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.services.student.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(studentRepository.findAll());
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

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<Course>> getAllFinishedCourses(@PathVariable String studentId){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getFinishedCourses(studentId));
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> addFinishedCourse(@PathVariable String studentId, @PathVariable String courseId){
        studentService.addFinishedCourse(studentId, courseId);
        return ResponseEntity.ok(studentRepository.getReferenceById(studentId));
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

}
