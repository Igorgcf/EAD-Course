package com.ead.course.dtos;

import com.ead.course.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID id;

    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String actionType;

    List<CourseDTO> courses =  new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(UUID id, String username, String email, String fullName, String userStatus, String userType, String phoneNumber, String cpf, String imageUrl, String actionType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.userStatus = userStatus;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
        this.actionType = actionType;
    }

    public UserDTO(User entity){
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.fullName = entity.getFullName();
        this.userStatus = entity.getUserStatus();
        this.userType = entity.getUserType();
        this.phoneNumber = entity.getPhoneNumber();
        this.cpf = entity.getCpf();
        this.imageUrl = entity.getImageUrl();
    }

    public User convertToUser(){
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setFullName(this.fullName);
        user.setUserStatus(this.userStatus);
        user.setUserType(this.userType);
        user.setPhoneNumber(this.phoneNumber);
        user.setCpf(this.cpf);
        user.setImageUrl(this.imageUrl);
        return user;
    }
}
