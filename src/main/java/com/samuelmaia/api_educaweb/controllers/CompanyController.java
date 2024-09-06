package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.company.Company;
import com.samuelmaia.api_educaweb.models.company.CompanyGetDTO;
import com.samuelmaia.api_educaweb.models.company.CompanyPostDTO;
import com.samuelmaia.api_educaweb.models.response.ErrorResponse;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRequestPost;
import com.samuelmaia.api_educaweb.repositories.CompanyRepository;
import com.samuelmaia.api_educaweb.repositories.VacancyRepository;
import com.samuelmaia.api_educaweb.services.company.CompanyService;
import com.samuelmaia.api_educaweb.services.vacancy.VacancyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    CompanyService companyService;
    @Autowired
    VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<CompanyGetDTO>> getAllCompanies(){
        return ResponseEntity.status(HttpStatus.OK).body(companyRepository.findAll().stream().map(company -> companyService.generateGetDTO(company)).toList());
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable String companyId){
        try{
            Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
            return ResponseEntity.status(HttpStatus.OK).body(companyService.generateGetDTO(company));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Company> registerCompany(@RequestBody @Validated CompanyPostDTO data){
        Company company = new Company(data);
        company.setPassword(encoder.encode(company.getPassword()));
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @PostMapping("/{companyId}/vacancy")
    public ResponseEntity<?> createVacancy(@RequestBody @Validated VacancyRequestPost data, @PathVariable String companyId){
        try{
            String vacancyId = companyService.createVacancy(companyId, data);
            return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.generateGetDTO(vacancyRepository.getReferenceById(vacancyId)));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }


}
