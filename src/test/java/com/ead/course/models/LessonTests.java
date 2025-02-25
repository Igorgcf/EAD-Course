package com.ead.course.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class LessonTests {

    @Test
    public void lessonShouldHaveCorrectStructure(){

        Lesson entity = new Lesson();

        Module module = new Module();

        UUID id = UUID.randomUUID();

        module.setId(null);
        module.setTitle("Title 01");
        module.setDescription("Description 01");
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        entity.setId(id);
        entity.setTitle("Title 01");
        entity.setDescription("Description 01");
        entity.setVideoUrl("www.video.com");
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setModule(module);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getModule());
        Assertions.assertEquals(entity.getId(), id);
        Assertions.assertEquals(entity.getTitle(), "Title 01");
        Assertions.assertEquals(entity.getDescription(), "Description 01");
        Assertions.assertEquals(entity.getVideoUrl(), "www.video.com");
    }
}
