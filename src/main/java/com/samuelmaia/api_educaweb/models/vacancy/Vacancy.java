package com.samuelmaia.api_educaweb.models.vacancy;

import com.samuelmaia.api_educaweb.models.company.Company;
import com.samuelmaia.api_educaweb.models.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "vacancies")
@Entity(name = "vacancies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Vacancy {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column
    private String arrangement;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double salary;

    @ManyToMany(mappedBy = "vacancies")
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Vacancy(VacancyRequestPost data){
        this.title = data.title();
    }
}
