package com.samuelmaia.api_educaweb.services.student;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRepository;
import com.samuelmaia.api_educaweb.models.student.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public List<Course> getFinishedCourses(String studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));
        return student.getCourses();
    }

    public void addFinishedCourse(String studentId, String courseId){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        student.getCourses().add(course);
        studentRepository.save(student);
    }
}
