package com.samuelmaia.api_educaweb.models.student;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "students")
@Entity(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "student_vacancy",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> vacancies = new ArrayList<>();

    public Student(StudentRequestPost data){
        this.email = data.email();
        this.firstName = data.first_name();
        this.lastName = data.last_name();
        this.login = data.login();
        this.password = data.password();
    }
}
