package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
