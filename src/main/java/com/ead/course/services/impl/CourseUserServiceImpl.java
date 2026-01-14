package com.ead.course.services.impl;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.NotificationDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.enums.UserStatus;
import com.ead.course.models.Course;
import com.ead.course.models.User;
import com.ead.course.publishers.NotificationPublisher;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.CourseUserService;
import com.ead.course.services.exceptions.BadRequestException;
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
    private CourseRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationPublisher publisher;

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UserDTO dto) {

        log.debug("Subscription userId received {}", dto.getId());

        Optional<Course> obj = repository.findById(courseId);
        Course entity = obj.orElseThrow(() -> new ResourceNotFoundException("Course id not found: " + courseId));

        Optional<User> opt = userRepository.findById(dto.getId());
        User user = opt.orElseThrow(() -> new ResourceNotFoundException("User id not found: " + dto.getId()));

        if(user.getUserStatus().equals(UserStatus.BLOCKED.toString())){
            throw new BadRequestException("User is blocked!");
        }

        boolean exists = repository.existsByCourseAndUser(courseId, dto.getId());
        if(exists){
            throw new BadRequestException("Subscription already exists!");
        }

        repository.saveSubscriptionUserInCourse(courseId, dto.getId());

        log.debug("Subscription saved {}, {}", entity.getId(), dto.getId());
        log.info("Subscription saved successfully courseId: {}, userId: {}", entity.getId(),
                (dto.getId() != null ? dto.getId() : "No user id provided"));
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourseAndSendNotification(CourseDTO courseDto, UserDTO userDto) {

        saveSubscriptionUserInCourse(courseDto.getId(), userDto);


        try{
        NotificationDTO notificationDTO = new NotificationDTO();
        copyDtoToEntity(notificationDTO, courseDto, userDto);
        publisher.publishNotificationCommand(notificationDTO);
        }catch(Exception e){
            log.warn("Error sending notification!");
        }
    }

    void copyDtoToEntity(NotificationDTO notificationDTO, CourseDTO courseDto, UserDTO userDto){

        notificationDTO.setTitle("Welcome to the course: " + courseDto.getName());
        notificationDTO.setUserId(userDto.getId());
        notificationDTO.setMessage("Subscribed to course successfully! Course name: " + courseDto.getName());


    }

}
