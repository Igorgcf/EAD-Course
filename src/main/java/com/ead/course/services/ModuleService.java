package com.ead.course.services;

import com.ead.course.dtos.ModuleDTO;
import com.ead.course.models.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ModuleService {

    Page<ModuleDTO> findAllPaged(Specification<Module> spec, Pageable pageable);

    Page<ModuleDTO> queryMethod(Specification<Module> spec, Pageable pageable);

    ModuleDTO findById(UUID id);

    ModuleDTO insert(ModuleDTO dto);

    ModuleDTO update(UUID id, ModuleDTO dto);

    void deleteById(UUID id);
}
