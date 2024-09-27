package com.samuelmaia.api_educaweb.services.instructor;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.course.CourseRequestPost;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.response.DeleteResponse;
import com.samuelmaia.api_educaweb.models.response.LoginResponse;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.repositories.InstructorRepository;
import com.samuelmaia.api_educaweb.services.course.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    PasswordEncoder encoder;

    public String createCourse(String instructorId, CourseRequestPost data){
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado"));
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
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado"));
        return instructor.getCourses().stream().map(course -> courseService.generateGetDTO(course)).toList();
    }

    public Boolean login(String login, String password){
        Instructor instructor = instructorRepository.findByLogin(login);

        if (instructor == null) throw new EntityNotFoundException("Instrutor com este login não existe");

        if (encoder.matches(password, instructor.getPassword())){
            return true;
        }
        return false;
    }

    public ResponseEntity<DeleteResponse> deleteCourse(String courseId, String instructorId){
        try{
            Course courseToBeDeleted = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Id de curso não encontrado."));
            Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Id de instrutor não encontrado"));
            System.out.println(instructor.getCourses());
            instructor.getCourses().remove(courseToBeDeleted);
            System.out.println(instructor.getCourses());
            instructorRepository.save(instructor);

            return ResponseEntity.ok(new DeleteResponse(true, "Curso deletado com sucesso!"));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteResponse(false, e.getMessage()));
        }
    }
}