package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
