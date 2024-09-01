package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.error_response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.student.*;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.services.student.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @Operation(summary = "Consula por estudantes",description = "Realiza uma consulta pelos estudantes do sistema, retornando uma lista com todos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os estudantes são retornados."),
    })
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok().body(studentRepository.findAll());
    }

    @Operation(summary = "Consulta por id",description = "Realiza uma consulta por um estudante específico pelo id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o estudante com o id passado como parâmetro pela url"),
            @ApiResponse(responseCode = "404", description = "Retorna null caso não seja encontrado nenhum estudante com o id")
    })
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

    @Operation(summary = "Novo estudante", description = "Recebe os dados de um estudante pelo body da requisição e cria um novo registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante foi criado e é retornado o objeto que foi adicionado ao banco"),
            @ApiResponse(responseCode = "500", description = "Caso ocorra algum erro interno, é retornado o objeto null e o status 500")
    })
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

    @Operation(summary = "Cursos finalizados", description = "Retorna uma lista de cursos finalizados do estudante com id passado pela url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os cursos do estudante"),
            @ApiResponse(responseCode = "404", description = "É retornado null, caso o estudante não seja encontrado")
    })
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

    @PutMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@Parameter(description = "id do usuário a ser atualizado") @PathVariable String studentId, @RequestBody @Validated StudentRequestPut data){
        try{
            Student updatedStudent = studentService.updateStudent(studentId, data);
            studentRepository.save(updatedStudent);
            return ResponseEntity.ok().body(updatedStudent);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno"  ));
        }
    }

    

}
