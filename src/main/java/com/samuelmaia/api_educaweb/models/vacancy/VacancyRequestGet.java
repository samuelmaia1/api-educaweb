package com.samuelmaia.api_educaweb.models.vacancy;

import com.samuelmaia.api_educaweb.models.company.CompanyGetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VacancyRequestGet {
    private String title;
    private String arrangement;
    private String description;
    private Double salary;
    private CompanyGetDTO company;

    public VacancyRequestGet(String title, String arrangement, String description, Double salary) {
        this.title = title;
        this.arrangement = arrangement;
        this.description = description;
        this.salary = salary;
    }
}
