package com.samuelmaia.api_educaweb.controllers;

import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRepository;
import com.samuelmaia.api_educaweb.models.vacancy.VacancyRequestPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vacancy")
public class VacancyController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies(){
        return ResponseEntity.ok(vacancyRepository.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<Vacancy> registerVacancy(@RequestBody @Validated VacancyRequestPost data){
        try {
            Vacancy vacancy = new Vacancy(data);
            vacancyRepository.save(vacancy);
            return ResponseEntity.status(HttpStatus.CREATED).body(vacancy);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
