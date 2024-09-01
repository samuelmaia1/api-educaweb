package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
