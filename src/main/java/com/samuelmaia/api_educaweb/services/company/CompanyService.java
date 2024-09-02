package com.samuelmaia.api_educaweb.services.company;

import com.samuelmaia.api_educaweb.models.company.Company;
import com.samuelmaia.api_educaweb.models.company.CompanyGetDTO;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRequestPost;
import com.samuelmaia.api_educaweb.repositories.CompanyRepository;
import com.samuelmaia.api_educaweb.repositories.VacancyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    public String createVacancy(@PathVariable String companyId, VacancyRequestPost vacancyData){
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
        Vacancy vacancy = new Vacancy(vacancyData, company);
        company.getVacancies().add(vacancy);
        companyRepository.save(company);
        vacancyRepository.save(vacancy);
        return vacancy.getId();
    }

    public CompanyGetDTO generateGetDTO(Company company){
        return new CompanyGetDTO(company.getName(), company.getEmail(), company.getCountry(), company.getState(), company.getCity(), company.getAddress());
    }
}
