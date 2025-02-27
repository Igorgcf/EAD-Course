package com.ead.course.services;

import com.ead.course.dtos.CourseUserDTO;
import com.ead.course.dtos.UserDTO;

import java.util.UUID;

public interface CourseUserService {

    CourseUserDTO saveAndSendSubscriptionUserInCourse(UUID courseId, UserDTO dto);
}
