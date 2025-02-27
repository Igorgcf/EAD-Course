package com.ead.course.services.impl;

import com.ead.course.clients.CourseClient;
import com.ead.course.dtos.CourseUserDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.enums.UserStatus;
import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private CourseClient client;

    @Transactional
    @Override
    public CourseUserDTO saveAndSendSubscriptionUserInCourse(UUID courseId, UserDTO dto) {

        Optional<Course> obj = repository.findById(courseId);
        Course entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + courseId));

        boolean existsByCourseIdAndUserId = courseUserRepository.existsByCourseIdAndUserId(courseId, dto.getId());
        if(existsByCourseIdAndUserId){
            throw new RuntimeException("User already subscribed to this course");
        }

        UserDTO userDTO = client.findById(dto.getId());
        if(userDTO.getUserStatus().equals(UserStatus.BLOCKED)){
            throw new RuntimeException("User is blocked");
        }

        CourseUser courseUser = entity.convertToCourseUser(dto.getId());

        courseUserRepository.save(courseUser);

        log.debug("CourseUser Id saved {}, CourseUser userId saved {}, CourseUser user saved {}", courseUser.getId(),
                (courseUser.getUserId() != null ? courseUser.getUserId() : "No userId associated"),
                (courseUser.getCourse() != null ? courseUser.getCourse() : "No course associated"));

        log.info("CourseUser Id saved {}, CourseUser userId saved {}, CourseUser courseId saved {}", courseUser.getId(),
                (courseUser.getUserId() != null ? courseUser.getUserId() : "No userId associated"),
                (courseUser.getCourse().getId() != null ? courseUser.getCourse().getId() : "No courseId associated"));

        client.postSubscriptionUserInCourse(courseUser.getCourse(), courseUser.getUserId());

        return new CourseUserDTO(courseUser.getId(), courseUser.getUserId(), courseUser.getCourse());

    }
}
