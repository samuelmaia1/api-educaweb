package com.samuelmaia.api_educaweb.models.vacancy;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true)
    private String title;

    public Vacancy(VacancyRequestPost data){
        this.title = data.title();
    }
}
