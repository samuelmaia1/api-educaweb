package com.samuelmaia.api_educaweb.models.student;

import com.samuelmaia.api_educaweb.models.course.Course;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private List<Course> courses;
}
