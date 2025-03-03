package com.ead.course.dtos;

import com.ead.course.models.Course;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CourseUserDTO {

    private UUID id;
    private UUID userId;
    private CourseDTO courseDTO;

    public CourseUserDTO(){
    }

    public CourseUserDTO(UUID id, UUID userId, Course course) {
        this.id = id;
        this.userId = userId;
        this.courseDTO = new CourseDTO(course);
    }
}
