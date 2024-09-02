package com.samuelmaia.api_educaweb.services.vacancy;

import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRequestGet;
import com.samuelmaia.api_educaweb.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacancyService {
    @Autowired
    CompanyService companyService;

    public VacancyRequestGet generateGetDTO(Vacancy vacancy){
        return new VacancyRequestGet(vacancy.getTitle(), vacancy.getArrangement(), vacancy.getDescription(), vacancy.getSalary(), companyService.generateGetDTO(vacancy.getCompany()));
    }
}
