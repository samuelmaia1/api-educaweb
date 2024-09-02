package com.samuelmaia.api_educaweb.services.student;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.repositories.CourseRepository;
import com.samuelmaia.api_educaweb.models.student.*;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.repositories.VacancyRepository;
import com.samuelmaia.api_educaweb.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    PasswordEncoder encoder;

    public List<Course> getFinishedCourses(String studentId){
        try{
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));
            return student.getCourses();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addFinishedCourse(String studentId, String courseId){
        try{
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
            student.getCourses().add(course);
            studentRepository.save(student);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public List<Vacancy> getAllVacancies(String studentId){
        try{
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));
            return student.getVacancies();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addVacancy(String studentId, String vacancyId){
        try{
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));
            Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));
            student.getVacancies().add(vacancy);
            studentRepository.save(student);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public StudentRequestGet generateStudentGetDTO(Student student){
        StudentRequestGet studentDTO = new StudentRequestGet(student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getLogin(),
                student.getEmail(),
                student.getCourses(),
                student.getVacancies()
        );
        return studentDTO;
    }

    public Student updateStudent(String id, StudentRequestPut data){
        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));

        if (data.email() != null) student.setEmail(data.email());
        if (data.firstName() != null) student.setFirstName(data.firstName());
        if (data.lastName() != null) student.setLastName(data.lastName());
        if (data.password() != null) {
            student.setPassword(encoder.encode(data.password()));
        }
        if (data.login() != null) student.setLogin(data.login());

        return student;
    }
}
