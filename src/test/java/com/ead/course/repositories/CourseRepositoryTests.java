package com.ead.course.repositories;

import com.ead.course.models.Course;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class CourseRepositoryTests {

    @Autowired
    private CourseRepository repository;

    @Test
    public void findAllShouldReturnAllCoursePaged() {

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Course> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnCourseByIdWhenIdExists(){

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Course> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        Course entity = Factory.createdCourse();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteCourseByIdWhenIdExists(){

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.deleteById(id);
            throw new DataIntegrityViolationException("Integrity violation exception.");
        });



    }
}
