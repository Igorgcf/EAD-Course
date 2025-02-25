package com.ead.course.controllers;

import com.ead.course.clients.CourseClient;
import com.ead.course.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3700)
@RestController
public class CourseUserController {

    @Autowired
    private CourseClient courseClient;

    @GetMapping(value = "/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> findAllUserByCourse(@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                              @PathVariable(value = "courseId") UUID courseId){

        Page<UserDTO> page = courseClient.findAllUsersByCourse(courseId, pageable);

        return ResponseEntity.ok().body(page);
    }
}
