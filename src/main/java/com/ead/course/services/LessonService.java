package com.ead.course.services;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface LessonService {

    Page<LessonDTO> findAllPaged(Specification<Lesson> spec, Pageable pageable);

    Page<LessonDTO> queryMethod(Specification<Lesson> spec, Pageable pageable);

    LessonDTO findById(UUID id);

    LessonDTO insert(LessonDTO dto);

    LessonDTO update (UUID id, LessonDTO dto);

    LessonDTO updateModule(UUID id, LessonDTO dto);

    void deleteById(UUID id);
}
