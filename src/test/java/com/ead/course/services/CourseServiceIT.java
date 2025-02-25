package com.ead.course.services;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.models.Course;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.services.impl.CourseServiceImpl;
import com.ead.course.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@SpringBootTest
public class CourseServiceIT {

    @Autowired
    private CourseServiceImpl service;

    @Autowired
    private CourseRepository repository;

    @Test
    public void findAllPagedShouldReturnAllCoursePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<CourseDTO> page = service.findAllPaged(null, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnCourseByIdWhenIdExists(){

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CourseDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        CourseDTO dto = Factory.createdCourseDto();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenCorrectStructureAndIdExists(){

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CourseDTO dto = Factory.createdCourseDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateNameShouldUpdateNameWhenCorrectStructureAndIdExists(){

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CourseDTO dto = Factory.createdCourseDtoToUpdateName();

        service.updateName(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteCourseByIdWhenIdExists(){

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(2, repository.count());
    }
}
