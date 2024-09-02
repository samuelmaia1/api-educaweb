package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, String> { }
