package com.ead.course.controllers;

import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3700)
@RestController
public class CourseUserController {

    @Autowired
    private UserService service;

    private CourseService courseService;

    @PostMapping(value = "/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveAndSendSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId
                                                                             //@Validated(UserDTO.UserView.RegistrationPost.class)
                                                                             ) {
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
