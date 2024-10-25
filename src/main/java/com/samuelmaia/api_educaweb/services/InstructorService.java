package com.samuelmaia.api_educaweb.services;

import com.samuelmaia.api_educaweb.exceptions.DataIsNotValidException;
import com.samuelmaia.api_educaweb.exceptions.UserNameNotFoundException;
import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.instructor.InstructorPostDTO;
import com.samuelmaia.api_educaweb.models.response.DeleteResponse;
import com.samuelmaia.api_educaweb.repositories.CompanyRepository;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    PasswordEncoder encoder;

    public String createCourse(String instructorId, CourseRequestPost data){
        if (!courseRepository.findByName(data.name()).isEmpty()) throw new DataIsNotValidException("Chave (nome) já existente");
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new UserNameNotFoundException("Instrutor não encontrado"));
        Course course = new Course(data, instructor);
        instructor.getCourses().add(course);
        courseRepository.save(course);
        instructorRepository.save(instructor);
        return course.getId();
    }

    public InstructorGetDTO generateGetDTO(Instructor instructor){
        return new InstructorGetDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getCourses().stream().map(course -> courseService.generateGetDTO(course)).toList()
        );
    }

    public List<CourseRequestGet> getCourses(String instructorId){
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new UserNameNotFoundException("Instrutor não encontrado"));
        return instructor.getCourses().stream().map(course -> courseService.generateGetDTO(course)).toList();
    }

    public Instructor register(InstructorPostDTO data){
        if (instructorRepository.existsByLogin(data.login()) ||studentRepository.existsByLogin(data.login()) || companyRepository.existsByLogin(data.login())){
            throw new DataIntegrityViolationException("Nome de usuário já cadastrado.");
        }
        if (instructorRepository.existsByEmail(data.email()) || studentRepository.existsByEmail(data.email()) || companyRepository.existsByEmail(data.email())){
            throw new DataIntegrityViolationException("Email já cadastrado.");
        }

        Instructor instructor = new Instructor(data);
        instructor.setPassword(encoder.encode(instructor.getPassword()));
        instructorRepository.save(instructor);

        return instructor;
    }

    public Boolean login(String login, String password){
        Instructor instructor = instructorRepository.findByLogin(login);

        if (instructor == null) throw new UserNameNotFoundException();

        if (encoder.matches(password, instructor.getPassword())){
            return true;
        }
        return false;
    }

    public ResponseEntity<DeleteResponse> deleteCourse(String courseId, String instructorId){
        try{
            Course courseToBeDeleted = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Id de curso não encontrado."));
            Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Id de instrutor não encontrado"));
            instructor.getCourses().remove(courseToBeDeleted);
            instructorRepository.save(instructor);

            return ResponseEntity.ok(new DeleteResponse(true, "Curso deletado com sucesso!"));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteResponse(false, e.getMessage()));
        }
    }
}