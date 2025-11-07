package com.ead.course.controllers;

import com.ead.course.dtos.UserDTO;
import com.ead.course.services.impl.CourseUserServiceImpl;
import com.ead.course.services.impl.UserServiceImpl;
import com.ead.course.specification.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CourseUserController {

    @Autowired
    private CourseUserServiceImpl service;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody UserDTO dto) {

        service.saveSubscriptionUserInCourse(courseId, dto);
        return ResponseEntity.ok().body("Subscription successfully! CourseId: " + courseId + " | " + " UserId: " + dto.getId());
    }

    @GetMapping(value = "/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> findAllUserByCourse(SpecificationTemplate.UserSpec spec,
                                                             @PageableDefault (page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC)
                                                             @PathVariable UUID courseId,
                                                             Pageable pageable){
        Page<UserDTO> page = userService.findAllPaged(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        return ResponseEntity.ok().body(page);
    }
}

