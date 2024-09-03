package com.samuelmaia.api_educaweb.models.course;

import com.samuelmaia.api_educaweb.models.instructor.InstructorGetDTOByCourse;
import com.samuelmaia.api_educaweb.services.instructor.InstructorService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Getter
@Setter
public class CourseRequestGet {
    private String id;
    private String name;
    private String category;
    private String description;
    private String url;

}
