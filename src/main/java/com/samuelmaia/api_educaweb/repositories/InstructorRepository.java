package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.instructor.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
}
