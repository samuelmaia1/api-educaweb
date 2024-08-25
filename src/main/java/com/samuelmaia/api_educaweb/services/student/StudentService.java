package com.samuelmaia.api_educaweb.services.student;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRepository;
import com.samuelmaia.api_educaweb.models.student.*;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private VacancyRepository vacancyRepository;

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
}
