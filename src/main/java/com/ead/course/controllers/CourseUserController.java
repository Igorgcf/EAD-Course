package com.ead.course.controllers;

import com.ead.course.dtos.UserDTO;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3700)
@RestController
public class CourseUserController {

    @Autowired
    private UserService service;

    private CourseService courseService;
    
    @GetMapping(value = "/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> findAllUserByCourse(@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "courseId") UUID courseId) {

        Page<UserDTO> page = null;
        return ResponseEntity.ok().body(page);
    }

    @PostMapping(value = "/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveAndSendSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                             @Validated(UserDTO.UserView.RegistrationPost.class)
                                                                             @RequestBody @Valid UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
