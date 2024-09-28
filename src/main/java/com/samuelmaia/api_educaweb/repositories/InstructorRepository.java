package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
    Optional<Instructor> findByEmail(String email);

    Instructor findByLogin(String login);

    Boolean existsByEmail(String email);

    Boolean existsByLogin(String login);
}
