package com.ead.course.dtos;

import com.ead.course.models.Lesson;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LessonDTO {

    public interface LessonView{
        public static interface registrationPost {}
        public static interface lessonPut {}
        public static interface modulePut {}
    }

    @JsonView({ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    private UUID id;

    @JsonView({ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class, LessonView.registrationPost.class, LessonView.lessonPut.class})
    @NotBlank(message = "The title field is mandatory and only blank spaces are not allowed.", groups = LessonView.registrationPost.class)
    private String title;

    @JsonView({ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class, LessonView.registrationPost.class, LessonView.lessonPut.class})
    @NotBlank(message = "The description field is mandatory and only blank spaces are not allowed.", groups = LessonView.registrationPost.class)
    private String description;

    @JsonView({ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class, LessonView.registrationPost.class, LessonView.lessonPut.class})
    private String videoUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @JsonView({LessonView.registrationPost.class, LessonView.modulePut.class, LessonView.lessonPut.class})
    private ModuleDTO module;

    public LessonDTO(){
    }

    public LessonDTO(UUID id, String title, String description, String videoUrl,
                     LocalDateTime creationDate, LocalDateTime lastUpdateDate, ModuleDTO module) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.creationDate = creationDate;
        this.module = module;
        this.lastUpdateDate = lastUpdateDate;
    }

    public LessonDTO(Lesson entity){

        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        videoUrl = entity.getVideoUrl();
        creationDate = entity.getCreationDate();
        lastUpdateDate = entity.getLastUpdateDate();
        module = new ModuleDTO(entity.getModule());
    }
}
