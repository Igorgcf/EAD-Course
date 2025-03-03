package com.ead.course.services.impl;

import com.ead.course.clients.CourseClient;
import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.ModuleDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.enums.UserType;
import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.exceptions.BadRequestException;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseClient client;


    @Transactional(readOnly = true)
    @Override
    public Page<CourseDTO> findAllPaged(Specification<Course> spec, Pageable pageable) {

        Page<Course> page = repository.findAll(spec, pageable);
        return page.map(x -> new CourseDTO(x, x.getModules()));
    }

    @Transactional(readOnly = true)
    @Override
    public CourseDTO findById(UUID id) {

        Optional<Course> obj = repository.findById(id);
        Course entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        return new CourseDTO(entity, entity.getModules());
    }

    @Transactional
    @Override
    public CourseDTO insert(CourseDTO dto) {

        log.debug("Insert CourseDTO received: {}, ModuleDTO received: {} " , dto,
        (dto.getModules() != null ? dto.getModules() : "Modules no provided"));


        UserDTO userDTO = client.findById(dto.getInstructorId());

        if(userDTO.getUserType().equals(UserType.STUDENT)){
            throw new BadRequestException("User must be a instructor or admin.");
        }

        Course entity = new Course();
        copyDtoToEntity(entity, dto);
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Insert Course saved: {}, Modules saved: {} " , entity,
                (entity.getModules() != null ? entity.getModules() : "No modules associated"));

        log.info("Course saved successfully Id: {}", entity.getId());

        return new CourseDTO(entity, entity.getModules());
    }

    @Transactional
    @Override
    public CourseDTO update(UUID id, CourseDTO dto) {

        log.debug("Update CourseDTO received: {}, ModuleDTO received: {}" , dto,
                (dto.getModules() != null ? dto.getModules() : "Modules no provided"));

        Optional<Course> obj = repository.findById(id);
        Course entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        UserDTO userDTO = client.findById(dto.getInstructorId());

        if(userDTO.getUserType().equals(UserType.STUDENT)){
            throw new BadRequestException("User must be a instructor or admin.");
        }

        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        copyDtoToEntity(entity, dto);
        repository.save(entity);

        log.debug("Update Course saved {}, Modules saved {}" , entity,
                (entity.getModules() != null ? entity.getModules() : "No modules associated"));

        log.info("Course updated successfully Id: {}", entity.getId());

        return new CourseDTO(entity, entity.getModules());
    }

    @Transactional
    @Override
    public void updateName(UUID id, CourseDTO dto) {

        log.debug("Update name received {}" , dto.getName());

        Optional<Course> obj = repository.findById(id);
        Course entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        entity.setName(dto.getName());

        log.debug("Update name saved {}" , entity.getName());
        log.info("Name updated successfully course Id: {}", entity.getId());

        repository.save(entity);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {

        log.debug("DeleteById received Id: {}" , id);

        Optional<Course> obj  = repository.findById(id);
        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        List<Module> list = moduleRepository.findAllModulesIntoCourse(id);
        if(!list.isEmpty()){
            for(Module module : list){
                List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getId());
                 if(!lessons.isEmpty()){
                     lessonRepository.deleteAll(lessons);
                 }
            }
            moduleRepository.deleteAll(list);
        }

        List<CourseUser> courseUsers = courseUserRepository.findAllCourseUserIntoCourse(id);
        if(courseUsers != null && !courseUsers.isEmpty()){
            courseUserRepository.deleteAll(courseUsers);
        }

        repository.deleteById(id);

        log.debug("DeleteById course Id deleted: {}", id);
        log.info("Course deleted successfully Id: {}", id);
    }

    void copyDtoToEntity(Course entity, CourseDTO dto){

        if(dto.getName() != null){
            entity.setName(dto.getName());
        }else{
            entity.setName(entity.getName());
        }

        if(dto.getDescription() != null){
            entity.setDescription(dto.getDescription());
        }else{
            entity.setDescription(entity.getDescription());
        }

        if(dto.getImageUrl() != null){
            entity.setImageUrl(dto.getImageUrl());
        }else{
            entity.setImageUrl(entity.getImageUrl());
        }

        if(dto.getStatus() != null){
            entity.setStatus(dto.getStatus());
        }

        if(dto.getCourseLevel() != null){
            entity.setCourseLevel(dto.getCourseLevel());
        }

        if(dto.getInstructorId() != null){
            entity.setInstructorId(dto.getInstructorId());
        }

        entity.getModules().clear();

        if(dto.getModules() != null && !dto.getModules().isEmpty()){
            for(ModuleDTO moduleDTO : dto.getModules()){
                if(moduleDTO.getId() != null){
                    Optional<Module> obj = moduleRepository.findById(moduleDTO.getId());
                    Module module = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + moduleDTO.getId()));
                    entity.getModules().add(module);
                    module.setCourse(entity);
                }else{
                    Module newModule = new Module();
                    newModule.setTitle(moduleDTO.getTitle());
                    newModule.setDescription(moduleDTO.getDescription());
                    newModule.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
                    newModule.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
                    newModule.setCourse(entity);
                    entity.getModules().add(newModule);
                }
            }
        }

    }
}
