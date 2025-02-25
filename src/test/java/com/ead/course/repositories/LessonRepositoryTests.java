package com.ead.course.repositories;

import com.ead.course.models.Lesson;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class LessonRepositoryTests {

    @Autowired
    private LessonRepository repository;

    @Test
    public void findAllShouldReturnAllLessonPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Lesson> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnLessonByIdWhenIdExists(){

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Lesson> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        Lesson entity = Factory.createdLesson();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(8, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteLessonByIdWhenIdExists(){

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(6, repository.count());
    }
}
