package com.samuelmaia.api_educaweb.models.company;

import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "companies")
@Entity(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String address;

    @OneToMany(mappedBy = "company")
    List<Vacancy> vacancies = new ArrayList<>();
}
