package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDTO;
import com.ead.course.services.ModuleService;
import com.ead.course.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/modules")
@CrossOrigin(origins = "*", maxAge = 3700)
public class ModuleController {

    @Autowired
    private ModuleService service;

    @GetMapping
    public ResponseEntity<Page<ModuleDTO>> findAllPaged(SpecificationTemplate.ModuleSpec spec,
                                                        @PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC)
                                                        Pageable pageable){

        Page<ModuleDTO> page = service.findAllPaged(spec ,pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/course/{id}/modules")
    public ResponseEntity<Page<ModuleDTO>> queryMethod(@PathVariable UUID id,
                                                       SpecificationTemplate.ModuleSpec spec, Pageable pageable){

        Page<ModuleDTO> list = service.queryMethod(SpecificationTemplate.moduleCourseId(id).and(spec), pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ModuleDTO> findById(@PathVariable UUID id){

        ModuleDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ModuleDTO> insert(@JsonView(ModuleDTO.ModuleView.registrationPost.class)
                                                @Validated(ModuleDTO.ModuleView.registrationPost.class)
                                                @RequestBody @Valid ModuleDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ModuleDTO> update(@PathVariable UUID id,
                                                @JsonView(ModuleDTO.ModuleView.updatePut.class)
                                                @Validated(ModuleDTO.ModuleView.updatePut.class)
                                                @RequestBody @Valid ModuleDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Module deleted successfully.");
    }

}
