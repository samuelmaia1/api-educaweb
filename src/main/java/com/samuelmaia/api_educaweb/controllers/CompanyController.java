package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.company.Company;
import com.samuelmaia.api_educaweb.models.company.CompanyPostDTO;
import com.samuelmaia.api_educaweb.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("register")
    public ResponseEntity<Company> registerCompany(@RequestBody @Validated CompanyPostDTO data){
        Company company = new Company(data);
        company.setPassword(encoder.encode(company.getPassword()));
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }
}
