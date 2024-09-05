package com.samuelmaia.api_educaweb.models.company;

import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRequestGet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyGetDTO {
    private String id;
    private String name;
    private String email;
    private String state;
    private String city;
    private String address;
    private List<VacancyRequestGet> vacancies;

    public CompanyGetDTO(String id, String name, String email, String state, String city, String address, List<Vacancy> vacancies){
        this.id = id;
        this.name = name;
        this.email = email;
        this.state = state;
        this.city = city;
        this.address = address;
        this.vacancies = vacancies.stream().map(vacancy -> new VacancyRequestGet(vacancy.getTitle(), vacancy.getArrangement(), vacancy.getDescription(), vacancy.getSalary())).toList();
    }
}
