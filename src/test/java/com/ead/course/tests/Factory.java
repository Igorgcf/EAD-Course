package com.ead.course.tests;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.LessonDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.enums.Level;
import com.ead.course.enums.Status;
import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class Factory {

    @Autowired
    private static ModuleRepository repository;

    public static Course createdCourse(){

        UUID id = UUID.randomUUID();

        Course entity = new Course(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com",
                LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), Status.CONCLUDED, Level.BEGINNER , id);

        return entity;
    }

    public static Module createdModule() {

        UUID id = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        Course course = new Course(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com",
                LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), Status.CONCLUDED, Level.BEGINNER , uuid);

        Module entity = new Module(null, "Java Module 01", "Introduction to OOP in Java.", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), course);

        return entity;
    }


    public static Lesson createdLesson() {

        UUID id = UUID.randomUUID();

        Course course = new Course(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com",
                LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), Status.CONCLUDED, Level.BEGINNER , id);

        Module module = new Module(null, "Java Module 01", "Introduction to OOP in Java.", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), course);

        Lesson entity = new Lesson(null, "OOP pillar", "Abstraction.", "www.video.com", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), module);

        return entity;
    }

    public static CourseDTO createdCourseDto() {

        UUID id = UUID.randomUUID();

        CourseDTO dto = new CourseDTO(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com", Status.CONCLUDED, Level.BEGINNER, id, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        return dto;
    }

    public static CourseDTO createdCourseDtoToUpdate() {

        UUID id = UUID.randomUUID();

        CourseDTO dto = new CourseDTO(null, "Spring", "Basic to advanced.", "www.image.com", Status.CONCLUDED, Level.BEGINNER, id, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        return dto;
    }

    public static CourseDTO createdCourseDtoToUpdateName(){

        CourseDTO dto = new CourseDTO("Spring 2.0");

        return dto;
    }

    public static ModuleDTO createdModuleDto() {

        UUID id = UUID.randomUUID();

        CourseDTO courseDTO =  new CourseDTO(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com", Status.CONCLUDED, Level.BEGINNER, id, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        ModuleDTO dto = new ModuleDTO(null, "Java Module 01", "Introduction to OOP in Java.", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), courseDTO);

        return dto;
    }

    public static ModuleDTO createdModuleDtoToUpdate() {

        UUID id = UUID.randomUUID();

        CourseDTO courseDTO =  new CourseDTO(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com", Status.CONCLUDED, Level.BEGINNER, id, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        ModuleDTO dto = new ModuleDTO(null, "Java Module 01", "Introduction to OOP in Java.", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), courseDTO);

        return dto;
    }

    public static LessonDTO createdLessonDto(UUID moduleId) {

        UUID id = UUID.randomUUID();

        CourseDTO courseDTO =  new CourseDTO(null, "Java + Spring", "Java and Spring from basic to advanced.", "www.image.com", Status.CONCLUDED, Level.BEGINNER, id, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        ModuleDTO moduleDTO = new ModuleDTO(moduleId, "Java Module 01", "Introduction to OOP in Java.", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), courseDTO);

        LessonDTO dto = new LessonDTO(null, "OOP pillar", "Abstraction.", "www.video.com", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")), moduleDTO);

        dto.setModule(moduleDTO);

        return dto;
    }

    public static LessonDTO createdLessonDtoToUpdate(){

        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(null);
        lessonDTO.setTitle("OOP pillar");
        lessonDTO.setDescription("Abstraction.");
        lessonDTO.setVideoUrl("www.video.com");
        lessonDTO.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonDTO.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return lessonDTO;
    }

    public static LessonDTO createdLessonDtoUpdateModule(){

        UUID id = UUID.randomUUID();

        LessonDTO lessonDTO = new LessonDTO();

        ModuleDTO moduleDTO = new ModuleDTO();

        moduleDTO.setId(id);
        moduleDTO.setTitle("Java Module 01");
        moduleDTO.setDescription("Introduction to OOP in Java.");

        lessonDTO.setModule(moduleDTO);

        return lessonDTO;
    }
}
