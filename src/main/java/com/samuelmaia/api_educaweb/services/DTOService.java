package com.samuelmaia.api_educaweb.services;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import com.samuelmaia.api_educaweb.models.student.Student;
import com.samuelmaia.api_educaweb.models.student.StudentRequestGet;
import org.springframework.stereotype.Service;

@Service
public class DTOService {
    public StudentRequestGet student(Student student){
        return new StudentRequestGet(student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getLogin(),
                student.getEmail(),
                student.getCourses().stream().map(course -> this.course(course)).toList(),
                student.getVacancies()
        );
    }

    public CourseRequestGet course(Course course){
        return new CourseRequestGet(course.getId(),
                course.getName(),
                course.getCategory(),
                course.getDescription(),
                course.getUrl(),
                this.instructor(course.getInstructor(), false));
    }

    public InstructorGetDTO instructor(Instructor instructor, boolean withCourse){
        if (!withCourse)
            return new InstructorGetDTO(
                    instructor.getId(),
                    instructor.getName(),
                    instructor.getEmail()
            );
        else
            return new InstructorGetDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getCourses().stream().map(course -> this.course(course)).toList()
            );
    }
}
