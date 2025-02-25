package com.ead.course.controllers;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.services.CourseService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/courses")
@CrossOrigin(origins = "*", maxAge = 3700)
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> findAllPaged(SpecificationTemplate.CourseSpec spec,
                                                        @PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                        @RequestParam(required = false) UUID userId){

        Page<CourseDTO> page = null;
        if(userId != null){
            page = service.findAllPaged(SpecificationTemplate.courseUserId(userId).and(spec), pageable);
        }else {
            page = service.findAllPaged(spec, pageable);
        }

        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable UUID id){

        CourseDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> insert(@JsonView(CourseDTO.CourseView.registrationPost.class)
                                              @Validated(CourseDTO.CourseView.registrationPost.class)
                                              @RequestBody @Valid CourseDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable UUID id,
                                            @JsonView(CourseDTO.CourseView.coursePut.class)
                                            @Validated(CourseDTO.CourseView.coursePut.class)
                                            @RequestBody @Valid CourseDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}/name")
    public ResponseEntity<Object> updateName(@PathVariable UUID id,
                                             @JsonView(CourseDTO.CourseView.namePut.class)
                                             @Validated(CourseDTO.CourseView.namePut.class)
                                             @RequestBody @Valid CourseDTO dto){

        service.updateName(id, dto);
        return ResponseEntity.ok().body("Name updated successfully.");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Course deleted successfully.");
    }
}
