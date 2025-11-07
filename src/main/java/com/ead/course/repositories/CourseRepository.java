package com.ead.course.repositories;

import com.ead.course.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

    boolean existsByName(String name);

    @Query(value = "select count(*) > 0 FROM tb_course_user WHERE course_id= :courseId and user_id= :userId", nativeQuery = true)
    boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Query(value = "SELECT COUNT(*) > 0 FROM tb_course_user WHERE course_id= :courseId", nativeQuery = true)
    boolean existsCourseAndUserByCourseId(@Param("courseId") UUID courseId);

    @Query(value = "SELECT COUNT(*) > 0 FROM tb_course_user WHERE user_id= :userId", nativeQuery = true)
    boolean existsCourseAndUserByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "INSERT INTO tb_course_user VALUES (:courseId, :userId);", nativeQuery = true)
    void saveSubscriptionUserInCourse(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value = "DELETE FROM tb_course_user WHERE course_id= :courseId", nativeQuery = true)
    void deleteCourseUserByCourseId(@Param("courseId") UUID courseId);

    @Modifying
    @Query(value = "DELETE FROM tb_course_user WHERE user_id= :userId", nativeQuery = true)
    void deleteCourseUserByUserId(@Param("userId") UUID userId);
}
