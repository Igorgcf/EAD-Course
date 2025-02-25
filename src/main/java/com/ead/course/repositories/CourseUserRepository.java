package com.ead.course.repositories;

import com.ead.course.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
}
