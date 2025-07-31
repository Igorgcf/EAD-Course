package com.ead.course.dtos;

import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
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
    private String username;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String oldPassword;

    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private UserStatus userStatus;
    private UserType userType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;
}
