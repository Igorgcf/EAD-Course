package com.ead.course.services;

import com.ead.course.dtos.CourseUserDTO;
import com.ead.course.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {

    Page<CourseUserDTO> findAllPaged(Pageable pageable);

    CourseUserDTO saveAndSendSubscriptionUserInCourse(UUID courseId, UserDTO dto);

    void deleteCourseUserByUser(UUID userId);
}
