package com.ead.course.dtos;

import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ModuleDTO {

    public interface ModuleView {
        public static interface registrationPost {}
        public static interface updatePut {}
    }

    @JsonView({CourseDTO.CourseView.registrationPost.class, CourseDTO.CourseView.coursePut.class, LessonDTO.LessonView.registrationPost.class, LessonDTO.LessonView.modulePut.class, LessonDTO.LessonView.lessonPut.class})
    private UUID id;

    @JsonView({CourseDTO.CourseView.registrationPost.class, CourseDTO.CourseView.coursePut.class, ModuleView.registrationPost.class, ModuleView.updatePut.class, LessonDTO.LessonView.modulePut.class})
    @NotBlank(message = "The name field is mandatory and blank space is not allowed.", groups = {ModuleView.registrationPost.class})
    @Size(min = 2, max = 70, message = "The minimum characters allowed are 2 and maximum are 70.", groups = {ModuleView.registrationPost.class, ModuleView.updatePut.class})
    private String title;

    @JsonView({CourseDTO.CourseView.registrationPost.class, CourseDTO.CourseView.coursePut.class, ModuleView.registrationPost.class, ModuleView.updatePut.class, LessonDTO.LessonView.modulePut.class})
    @NotBlank(message = "The description field is mandatory and blank space is not allowed.", groups = {ModuleView.registrationPost.class})
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @JsonView({ModuleView.registrationPost.class, ModuleView.updatePut.class})
    private CourseDTO course;

    @JsonView({ModuleView.registrationPost.class, ModuleView.updatePut.class})
    private List<LessonDTO> lessons = new ArrayList<>();

    public ModuleDTO(){
    }

    public ModuleDTO(UUID id, String title, String description, LocalDateTime creationDate, LocalDateTime lastUpdateDate, CourseDTO course) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.course = course;
    }

    public ModuleDTO(Module entity){

        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        creationDate = entity.getCreationDate();
        lastUpdateDate = entity.getLastUpdateDate();
        course = new CourseDTO(entity.getCourse());
    }

    public ModuleDTO(Module entity, List<Lesson> lessons){
        this(entity);
        lessons.forEach(x -> this.lessons.add(new LessonDTO(x)));
    }
}
