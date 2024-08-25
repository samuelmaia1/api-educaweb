package com.samuelmaia.api_educaweb.models.student;

import com.samuelmaia.api_educaweb.models.course.Course;
import com.samuelmaia.api_educaweb.models.vacancy.Vacancy;

import java.util.List;

public record StudentRequestGet(
        String id,
        String first_name,
        String last_name,
        String login,
        String email,
        List<Course> courses,
        List<Vacancy> vacancies
) { }
