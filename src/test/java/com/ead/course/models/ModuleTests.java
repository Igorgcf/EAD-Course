package com.ead.course.models;

import com.ead.course.enums.Level;
import com.ead.course.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class ModuleTests {

    @Test
    public void moduleShouldHaveCorrectStructure(){

        Module entity =  new Module();

        Course course = new Course();

        Lesson lesson = new Lesson();

        UUID id = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        course.setId(null);
        course.setName("Course 01");
        course.setDescription("Description 01");
        course.setStatus(Status.CONCLUDED);
        course.setCourseLevel(Level.BEGINNER);
        course.setInstructorId(uuid);
        course.setImageUrl("www.image.com");
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.getModules().add(entity);

        lesson.setId(null);
        lesson.setTitle("Title 01");
        lesson.setDescription("Description 01");
        lesson.setVideoUrl("www.video.com");
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(entity);

        entity.setId(id);
        entity.setTitle("Title 01");
        entity.setDescription("Description 01");
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setCourse(course);
        entity.getLessons().add(lesson);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getCourse());
        Assertions.assertNotNull(entity.getLessons());

        Assertions.assertEquals(entity.getId(), id);
        Assertions.assertEquals(entity.getTitle(), "Title 01");
        Assertions.assertEquals(entity.getDescription(), "Description 01");
    }
}
