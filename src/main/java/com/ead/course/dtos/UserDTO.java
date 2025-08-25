package com.ead.course.dtos;

import com.ead.course.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView{
        public static interface RegistrationPost{}
    }

    @JsonView(UserView.RegistrationPost.class)
    @NotNull(groups = UserView.RegistrationPost.class, message = "The field id is mandatory.")
    private UUID id;

    private String email;
    private String fullName;

    private String userStatus;
    private String userType;
    private String cpf;
    private String imageUrl;

    List<CourseDTO> courses =  new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(UUID id, String email, String fullName, String userStatus, String userType, String cpf, String imageUrl) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.userStatus = userStatus;
        this.userType = userType;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
    }

    public UserDTO(User entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.fullName = entity.getFullName();
        this.userStatus = entity.getUserStatus();
        this.userType = entity.getUserType();
        this.cpf = entity.getCpf();
        this.imageUrl = entity.getImageUrl();
    }
}
