package com.ead.course.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationDTO {

    private UUID userId;
    private String title;
    private String message;

    public NotificationDTO() {
    }

    public NotificationDTO(UUID userId, String title, String message) {
        this.userId = userId;
        this.title = title;
        this.message = message;
    }

}
