package com.ead.course.services;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.services.impl.LessonServiceImpl;
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
public class LessonServiceIT {

    @Autowired
    private LessonServiceImpl service;

    @Autowired
    private LessonRepository repository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    public void findAllPagedShouldReturnAllLessonPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<LessonDTO> page = service.findAllPaged(null,pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnLessonByIdWhenIdExists(){

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        Optional<Module> obj = moduleRepository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = Factory.createdLessonDto(id);

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(8, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenCorrectStructureAndIdExists(){

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = Factory.createdLessonDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(7, repository.count());
    }

    @Test
    public void updateModuleShouldSaveModuleWhenCorrectStructureAndIdExists(){

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = Factory.createdLessonDtoUpdateModule();

        dto = service.updateModule(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(7, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteLessonByIdWhenIdExists(){

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(6, repository.count());
    }
}
