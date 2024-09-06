package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByLogin(String login);
}