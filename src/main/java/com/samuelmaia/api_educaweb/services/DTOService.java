package com.samuelmaia.api_educaweb.services;

import com.samuelmaia.api_educaweb.models.company.Company;
import com.samuelmaia.api_educaweb.models.company.CompanyGetDTO;
import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.course.CourseRequestGet;
import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTO;
import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import com.samuelmaia.api_educaweb.models.student.Student;
import com.samuelmaia.api_educaweb.models.student.StudentRequestGet;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRequestGet;
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
                student.getVacancies().stream().map(vacancy -> this.vacancy(vacancy, true)).toList()
        );
    }

    public CompanyGetDTO company(Company company, boolean withVacancies){
        if (!withVacancies)
            return new CompanyGetDTO(
                    company.getId(),
                    company.getName(),
                    company.getEmail(),
                    company.getState(),
                    company.getCity(),
                    company.getAddress()
            );

        else
            return new CompanyGetDTO(
                    company.getId(),
                    company.getName(),
                    company.getEmail(),
                    company.getState(),
                    company.getCity(),
                    company.getAddress(),
                    company.getVacancies()
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

    public VacancyRequestGet vacancy(Vacancy vacancy, boolean withCompany){
        if (!withCompany)
            return new VacancyRequestGet(
                    vacancy.getTitle(),
                    vacancy.getArrangement(),
                    vacancy.getDescription(),
                    vacancy.getSalary()
            );
        else
            return new VacancyRequestGet(
                    vacancy.getTitle(),
                    vacancy.getArrangement(),
                    vacancy.getDescription(),
                    vacancy.getSalary(),
                    this.company(vacancy.getCompany(), false)
            );
    }
}
