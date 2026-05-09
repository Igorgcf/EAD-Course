package com.ead.course.controllers;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.services.impl.CourseServiceImpl;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/courses")
@CrossOrigin(origins = "*", maxAge = 3700)
public class CourseController {

    @Autowired
    private CourseServiceImpl service;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Page<CourseDTO>> findAllPaged(SpecificationTemplate.CourseSpec spec,
                                                        @PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                        @RequestParam(required = false) UUID userId){
        Page<CourseDTO> page;
        if(userId != null) {
        page = service.findAllPaged(SpecificationTemplate.courseUserId(userId).and(spec), pageable);
        }else{
            page = service.findAllPaged(spec, pageable);
        }
        return ResponseEntity.ok().body(page);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable UUID id){

        CourseDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<CourseDTO> insert(@JsonView(CourseDTO.CourseView.registrationPost.class)
                                            @Validated(CourseDTO.CourseView.registrationPost.class)
                                            @RequestBody @Valid CourseDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable UUID id,
                                            @JsonView(CourseDTO.CourseView.coursePut.class)
                                            @Validated(CourseDTO.CourseView.coursePut.class)
                                            @RequestBody @Valid CourseDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping(value = "/{id}/name")
    public ResponseEntity<Object> updateName(@PathVariable UUID id,
                                             @JsonView(CourseDTO.CourseView.namePut.class)
                                             @Validated(CourseDTO.CourseView.namePut.class)
                                             @RequestBody @Valid CourseDTO dto){

        service.updateName(id, dto);
        return ResponseEntity.ok().body("Name updated successfully.");
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Course deleted successfully.");
    }
}
