package com.ead.course.services;

import com.ead.course.dtos.ModuleDTO;
import com.ead.course.models.Module;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.services.impl.ModuleServiceImpl;
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
public class ModuleServiceIT {

    @Autowired
    private ModuleServiceImpl service;

    @Autowired
    private ModuleRepository repository;

    @Test
    public void findAllPagedShouldReturnAllModulePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ModuleDTO> page = service.findAllPaged(null, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnModuleByIdWhenIdExists(){

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ModuleDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        ModuleDTO dto = Factory.createdModuleDto();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenCorrectStructureAndIdExists(){

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ModuleDTO dto = Factory.createdModuleDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteModuleByIdWhenIdExists(){

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(3, repository.count());
    }
}
