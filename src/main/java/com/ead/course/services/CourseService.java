package com.ead.course.services;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {

    Page<CourseDTO> findAllPaged(Specification<Course> spec, Pageable pageable);

    CourseDTO findById(UUID id);

    CourseDTO insert(CourseDTO dto);

    CourseDTO update(UUID id, CourseDTO dto);

    void updateName (UUID id, CourseDTO dto);

    void deleteById(UUID id);
}
