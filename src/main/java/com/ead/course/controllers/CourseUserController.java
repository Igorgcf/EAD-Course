package com.ead.course.controllers;

import com.ead.course.clients.CourseClient;
import com.ead.course.dtos.CourseUserDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.CourseUserService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3700)
@RestController
public class CourseUserController {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private CourseUserService service;

    @GetMapping(value = "/courses/users")
    public ResponseEntity<Page<CourseUserDTO>> findAllPaged(Pageable pageable) {

        Page<CourseUserDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> findAllUserByCourse(@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "courseId") UUID courseId) {

        Page<UserDTO> page = courseClient.findAllUsersByCourse(courseId, pageable);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping(value = "/courses/{courseId}/users/subscription")
    public ResponseEntity<CourseUserDTO> saveAndSendSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                             @Validated(UserDTO.UserView.RegistrationPost.class)
                                                                             @RequestBody @Valid UserDTO dto) {

        CourseUserDTO response = service.saveAndSendSubscriptionUserInCourse(courseId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/courses/users/{userId}")
    public ResponseEntity<Object> deleteCourseUser(@PathVariable(value = "userId") UUID userId) {
        service.deleteCourseUserByUser(userId);
        return ResponseEntity.ok().body("User successfully unsubscribed from the course.");
    }
}
