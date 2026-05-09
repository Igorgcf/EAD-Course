package com.ead.course.controllers;

import com.ead.course.dtos.UserDTO;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){

        Page<UserDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }
}
