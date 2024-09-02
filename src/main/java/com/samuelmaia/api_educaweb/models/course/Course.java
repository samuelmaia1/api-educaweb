package com.samuelmaia.api_educaweb.models.course;

import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import com.samuelmaia.api_educaweb.models.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "courses")
@Entity(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String url;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    public Course(CourseRequestPost data){
        this.name = data.name();
        this.category = data.category();
        this.description = data.description();
        this.url = data.url();
    }

    public Course(CourseRequestPost data, Instructor instructor){
        this.name = data.name();
        this.category = data.category();
        this.description = data.description();
        this.url = data.url();
        this.instructor = instructor;
    }
}
