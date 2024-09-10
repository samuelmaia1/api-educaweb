package com.samuelmaia.api_educaweb.models.instructor;

import com.samuelmaia.api_educaweb.models.course.Course;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "instructors")
@Entity(name = "instructors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Instructor {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "login", unique = true)
    private String login;

    @OneToMany(mappedBy = "instructor", orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    public Instructor(InstructorPostDTO data){
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
    }
}
