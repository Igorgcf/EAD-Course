package com.ead.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    public String createUrl(UUID userId, Pageable pageable);
}
