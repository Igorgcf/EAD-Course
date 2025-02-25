package com.ead.course.models;

import com.ead.course.enums.Level;
import com.ead.course.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class CourseTests {

    @Test
    public void courseShouldHaveCorrectStructure(){

        Course entity = new Course();

        Module module = new Module();

        module.setId(null);
        module.setTitle("Title 01");
        module.setDescription("Description 01");
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(entity);

        UUID id = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        entity.setId(id);
        entity.setName("Course 01");
        entity.setDescription("Description 01");
        entity.setImageUrl("www.image.com");
        entity.setCourseLevel(Level.BEGINNER);
        entity.setStatus(Status.CONCLUDED);
        entity.setInstructorId(uuid);
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.getModules().add(module);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.getName(), "Course 01");
        Assertions.assertEquals(entity.getDescription(), "Description 01");
        Assertions.assertEquals(entity.getImageUrl(), "www.image.com");
        Assertions.assertEquals(entity.getCourseLevel(), Level.BEGINNER);
        Assertions.assertEquals(entity.getStatus(), Status.CONCLUDED);

        Assertions.assertNotNull(entity.getModules());
    }
}
