package com.ead.course.repositories;

import com.ead.course.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseIdAndUserId(UUID courseId, UUID userId);

    @Query(value = "SELECT * FROM tb_course_user WHERE course_id = :courseId", nativeQuery = true)
    List<CourseUser> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);

    List<CourseUser> findAllCourseUserByUserId(@Param("userId") UUID userId);
}
