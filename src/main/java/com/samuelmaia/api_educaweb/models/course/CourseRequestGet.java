package com.samuelmaia.api_educaweb.models.course;

public record CourseRequestGet(
        String id,
        String name,
        String category,
        String description,
        String url
) { }
