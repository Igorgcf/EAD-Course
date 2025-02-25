package com.ead.course.controllers;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.services.LessonService;
import com.ead.course.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/lessons")
@CrossOrigin(origins = "*", maxAge = 3700)
public class LessonController {

    @Autowired
    private LessonService service;

    @GetMapping
    public ResponseEntity<Page<LessonDTO>> findAllPaged(SpecificationTemplate.LessonSpec spec, Pageable pageable){

        Page<LessonDTO> page = service.findAllPaged(spec, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/modules/{id}/lessons")
    public ResponseEntity<Page<LessonDTO>> queryMethod(@PathVariable UUID id,
                                                       SpecificationTemplate.LessonSpec spec, Pageable pageable){

        Page<LessonDTO> page = service.queryMethod(SpecificationTemplate.lessonModuleId(id).and(spec), pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LessonDTO> findById(@PathVariable UUID id){

        LessonDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<LessonDTO> insert(@JsonView(LessonDTO.LessonView.registrationPost.class)
                                            @Validated(LessonDTO.LessonView.registrationPost.class)
                                            @RequestBody @Valid LessonDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LessonDTO> update(@PathVariable UUID id,
                                            @JsonView(LessonDTO.LessonView.lessonPut.class)
                                            @Validated(LessonDTO.LessonView.lessonPut.class)
                                            @RequestBody @Valid LessonDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/modules/lessons/{id}")
    public ResponseEntity<LessonDTO> updateModule(@PathVariable UUID id,
                                                  @JsonView(LessonDTO.LessonView.modulePut.class)
                                                  @Validated(LessonDTO.LessonView.modulePut.class)
                                                  @RequestBody @Valid LessonDTO dto){
        dto = service.updateModule(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Lesson deleted successfully.");
    }
}
