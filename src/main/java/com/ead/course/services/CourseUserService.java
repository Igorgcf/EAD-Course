package com.ead.course.services;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.UserDTO;

import java.util.UUID;

public interface CourseUserService {

     void saveSubscriptionUserInCourse(UUID courseId, UserDTO dto);

     void saveSubscriptionUserInCourseAndSendNotification(CourseDTO dto, UserDTO userDto);
}
