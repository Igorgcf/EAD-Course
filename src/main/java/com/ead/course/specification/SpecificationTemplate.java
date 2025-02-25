package com.ead.course.specification;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "status", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)

    })
    public interface CourseSpec extends Specification<Course>{
    }

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<Module>{
    }

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<Lesson>{
    }

    public static Specification<Module> moduleCourseId(final UUID id){
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");
            return cb.and(cb.equal(course.get("id"), id), cb.isMember(module, courseModules));
        };
    }

    public static Specification<Lesson> lessonModuleId(final UUID id){
        return (root, query, cd) -> {
            query.distinct(true);
            Root<Lesson> lesson = root;
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");
            return cd.and(cd.equal(module.get("id"), id), cd.isMember(lesson, moduleLessons));
        };
    }

    public static Specification<Course> courseUserId(final UUID userId){
        return(root, query, cb) -> {
            query.distinct(true);
            Join<Course, CourseUser> courseProd = root.join("courseUsers");
            return cb.equal(courseProd.get("userId"), userId);
        };
    }
}
