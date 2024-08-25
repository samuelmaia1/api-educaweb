package com.samuelmaia.api_educaweb.models.course;

import jakarta.persistence.*;
import lombok.*;

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

    private String category;

    public Course(CourseRequestPost data){
        this.name = data.name();
        this.category = data.category();
    }
}
