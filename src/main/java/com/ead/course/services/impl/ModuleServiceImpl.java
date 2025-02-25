package com.ead.course.services.impl;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository repository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<ModuleDTO> findAllPaged(Specification<Module> spec, Pageable pageable) {

        Page<Module> page = repository.findAll(spec, pageable);
        return page.map(x -> new ModuleDTO(x, x.getLessons()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ModuleDTO> queryMethod(Specification<Module> spec, Pageable pageable) {

        Page<Module> page = repository.findAll(spec, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("No courses were found.");
        }

        return page.map(x -> new ModuleDTO(x, x.getLessons()));
    }

    @Transactional(readOnly = true)
    @Override
    public ModuleDTO findById(UUID id) {

        Optional<Module> obj = repository.findById(id);
        Module entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        return new ModuleDTO(entity, entity.getLessons());
    }

    @Transactional
    @Override
    public ModuleDTO insert(ModuleDTO dto) {

        log.debug("Insert received ModuleDTO {}, CourseDTO received {}, LessonDTO received {}", dto,
                (dto.getCourse() != null ? dto.getCourse() : "Course no provided"),
                (dto.getLessons() != null ? dto.getLessons() : "Lessons no provided"));

        Module entity =  new Module();
        copyDtoToEntity(entity, dto, true, true);
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Insert Module saved {}, Course saved {}, Lesson Saved {}", entity,
                (entity.getCourse() != null ? entity.getCourse() : "No course associated"),
                (entity.getLessons() != null ? entity.getLessons() : "No lessons associated"));

        log.info("Module saved successfully Id: {}", entity.getId() );

        return new ModuleDTO(entity, entity.getLessons());
    }

    @Transactional
    @Override
    public ModuleDTO update(UUID id, ModuleDTO dto) {

        log.debug("Update ModuleDTO received {}, CourseDTO received {}, LessonDTO received {}", dto,
                (dto.getCourse() != null ? dto.getCourse() : "Course no provided"),
                (dto.getLessons() != null ? dto.getLessons() : "Lessons no provided"));

        Optional<Module> obj = repository.findById(id);
        Module entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto, true, true);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Update Module saved {}, Update Course save {}, Update Lesson saved {}", entity,
                (entity.getCourse() != null ? entity.getCourse() : "No course associated"),
                (entity.getLessons() != null ? entity.getLessons() : "No lessons associated"));

        log.info("Module updated successfully Id: {}", entity.getId());

        return new ModuleDTO(entity, entity.getLessons());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {

        log.debug("DeleteById received Id: {}", id);

        Optional<Module> obj = repository.findById(id);
        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        List<Lesson> list = lessonRepository.findAllLessonsIntoModule(id);
        if(!list.isEmpty()){
            lessonRepository.deleteAll(list);
        }

        repository.deleteById(id);

        log.debug("DeleteById Module Id deleted: {}", id);
        log.info("Module deleted successfully Id: {}", id);
    }

    void copyDtoToEntity(Module entity, ModuleDTO dto, boolean associateById, boolean createNewAssociations){

        if(dto.getTitle() != null){
            entity.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null){
            entity.setDescription(dto.getDescription());
        }

        if(associateById && dto.getCourse() != null && dto.getCourse().getId() != null){
            Optional<Course> obj = courseRepository.findById(dto.getCourse().getId());
            Course course = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getCourse().getId()));
            entity.setCourse(course);
            course.getModules().add(entity);
        }else if(createNewAssociations && dto.getCourse() != null){
            Course newCourse = new Course();
            newCourse.setName(dto.getCourse().getName());
            newCourse.setDescription(dto.getCourse().getDescription());
            newCourse.setImageUrl(dto.getCourse().getImageUrl());
            newCourse.setStatus(dto.getCourse().getStatus());
            newCourse.setCourseLevel(dto.getCourse().getCourseLevel());
            newCourse.setInstructorId(dto.getCourse().getInstructorId());
            newCourse.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            newCourse.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            newCourse.getModules().add(entity);
            entity.setCourse(newCourse);
        }

        entity.getLessons().clear();

        if(dto.getLessons() != null && !dto.getLessons().isEmpty()){
            for(LessonDTO lessonDTO : dto.getLessons()){
                if(associateById && lessonDTO.getId() != null){
                    Optional<Lesson> obj = lessonRepository.findById(lessonDTO.getId());
                    Lesson lesson = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + lessonDTO.getId()));
                    lesson.setModule(entity);
                    entity.getLessons().add(lesson);
                }else if(createNewAssociations && dto.getLessons() != null){
                    Lesson newLesson = new Lesson();

                    newLesson.setTitle(lessonDTO.getTitle());
                    newLesson.setDescription(lessonDTO.getDescription());
                    newLesson.setVideoUrl(lessonDTO.getVideoUrl());
                    newLesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
                    newLesson.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
                    newLesson.setModule(entity);
                    entity.getLessons().add(newLesson);
                }
            }
        }
    }
}
