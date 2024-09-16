package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
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

    @Operation(summary = "Consula por estudantes",description = "Realiza uma consulta pelos estudantes do sistema, retornando uma lista com todos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os estudantes são retornados."),
    })
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok().body(studentRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDTO loginData){
        try{
            if (studentService.login(loginData.login(), loginData.password())){
                var token = tokenService.generateStudentToken(studentRepository.findByLogin(loginData.login()));
                return ResponseEntity.ok(new LoginResponse(true, "Login efetuado com sucesso.", token));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Senha inválida.", ""));
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
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
            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.register(data));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Cursos finalizados", description = "Retorna uma lista de cursos finalizados do estudante com id passado pela url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os cursos do estudante"),
            @ApiResponse(responseCode = "404", description = "É retornado null, caso o estudante não seja encontrado")
    })
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getAllFinishedCourses(@PathVariable String studentId){
        try{
            List<CourseRequestGet> courseList = studentService.getFinishedCourses(studentId);
            return ResponseEntity.status(HttpStatus.OK).body(courseList);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @Operation(summary = "Novo curso finalizado", description = "Adiciona um novo curso à lista de cursos finalizados deste estudante")
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
