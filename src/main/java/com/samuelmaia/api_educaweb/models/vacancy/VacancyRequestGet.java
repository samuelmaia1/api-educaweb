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
}
