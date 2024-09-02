package com.samuelmaia.api_educaweb.repositories;

import com.samuelmaia.api_educaweb.models.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByName(String name);
    List<Course> findByCategory(String category);
    List<Course> findByCategoryAndName(String category, String name);
}
