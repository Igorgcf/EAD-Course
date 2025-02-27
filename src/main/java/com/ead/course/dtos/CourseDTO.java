package com.ead.course.dtos;

import com.ead.course.enums.Level;
import com.ead.course.enums.Status;
import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.models.Module;
import com.ead.course.validations.NameConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CourseDTO {

    public interface CourseView {
        public static interface registrationPost {}
        public static interface coursePut {}
        public static interface namePut {}
    }

    @JsonView({ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    private UUID id;

    @JsonView({CourseView.registrationPost.class, CourseView.namePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    @NotBlank(message = "The name field is mandatory and blank space is not allowed.", groups = {CourseView.registrationPost.class, CourseView.namePut.class})
    @Size(min = 2, max = 70, message = "The minimum characters allowed are 2 and maximum are 70.", groups = {CourseView.registrationPost.class, CourseView.namePut.class})
    @NameConstraint(message = "The name is already in use (not allowed).", groups = {CourseView.registrationPost.class, CourseView.namePut.class})
    private String name;

    @JsonView({CourseView.registrationPost.class, CourseView.coursePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    @NotBlank(message = "The description field is mandatory and blank space is not allowed.", groups = {CourseView.registrationPost.class, CourseView.coursePut.class})
    private String description;

    @JsonView({CourseView.registrationPost.class, CourseView.coursePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    private String imageUrl;

    @JsonView({CourseView.registrationPost.class, CourseView.coursePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    @NotNull(message = "The status field is mandatory.", groups = {CourseView.registrationPost.class, CourseView.coursePut.class})
    private Status status;

    @JsonView({CourseView.registrationPost.class, CourseView.coursePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    @NotNull(message = "The courseLevel field is mandatory.", groups = {CourseView.registrationPost.class, CourseView.coursePut.class})
    private Level courseLevel;

    @JsonView({CourseView.registrationPost.class, CourseView.coursePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    @NotNull(message = "The Instructor ID field cannot be null.", groups = {CourseView.registrationPost.class, CourseView.coursePut.class})
    private UUID instructorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @JsonView({CourseView.registrationPost.class, CourseView.coursePut.class, ModuleDTO.ModuleView.registrationPost.class, ModuleDTO.ModuleView.updatePut.class})
    private List<ModuleDTO> modules = new ArrayList<>();

    public CourseDTO(){
    }

    public CourseDTO(String name) {
        this.name = name;
    }

    public CourseDTO(UUID id, String name, String description, String imageUrl,
                     Status status, Level courseLevel, UUID instructorId, LocalDateTime creationDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.courseLevel = courseLevel;
        this.instructorId = instructorId;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public CourseDTO(Course entity){

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        imageUrl = entity.getImageUrl();
        status = entity.getStatus();
        courseLevel = entity.getCourseLevel();
        instructorId = entity.getInstructorId();
        creationDate = entity.getCreationDate();
        lastUpdateDate = entity.getLastUpdateDate();
    }

    public CourseDTO(Course entity, List<Module> modules){
        this(entity);
        modules.forEach(x -> this.modules.add(new ModuleDTO(x)));
    }

}
