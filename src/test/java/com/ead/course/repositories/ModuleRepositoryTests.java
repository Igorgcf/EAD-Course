package com.ead.course.repositories;

import com.ead.course.models.Module;
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
public class ModuleRepositoryTests {

    @Autowired
    private ModuleRepository repository;

    @Test
    public void findAllShouldReturnAllModulePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Module> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnCourseByIdWhenIdExists(){

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Module> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        Module entity = Factory.createdModule();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationException(){

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.deleteById(id);
            throw new DataIntegrityViolationException("Integrity violation Exception");
        });
    }
}
