package com.ead.course.services.impl;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.LessonService;
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
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository repository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<LessonDTO> findAllPaged(Specification<Lesson> spec, Pageable pageable) {

        Page<Lesson> page = repository.findAll(spec, pageable);
        return page.map(LessonDTO::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LessonDTO> queryMethod(Specification<Lesson> spec, Pageable pageable) {

        Page<Lesson> page = repository.findAll(spec, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("No modules were found." );
        }
        return page.map(LessonDTO::new);
    }

    @Transactional(readOnly = true)
    @Override
    public LessonDTO findById(UUID id) {

        Optional<Lesson> obj = repository.findById(id);
        Lesson entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        return new LessonDTO(entity);
    }

    @Transactional
    @Override
    public LessonDTO insert(LessonDTO dto) {

        log.debug("Insert LessonDTO received {}, ModuleDTO received {}", dto,
                (dto.getModule() != null ? dto.getModule() : "Modules no provided"));

        Lesson entity = new Lesson();
        copyDtoToEntity(entity, dto, true);
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Insert Lesson saved {}, Modules Saved {}", entity,
                (entity.getModule() != null ? entity.getModule(): "No modules Associated"));

        log.info("Lesson saved successfully Id: {}", entity.getId());

        return new LessonDTO(entity);
    }

    @Transactional
    @Override
    public LessonDTO update(UUID id, LessonDTO dto) {

        log.debug("Update LessonDTO received {}, ModuleDTO received {} ", dto,
                (dto.getModule() != null) ? dto.getModule() : "Modules no provided");

        Optional<Lesson> obj = repository.findById(id);
        Lesson entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto, true);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Update Lesson saved {}, Module Saved {}", entity,
                (entity.getModule() != null ? entity.getModule() : "No modules associated"));

        log.info("Lesson updated successfully Id: {}", entity.getId());

        return new LessonDTO(entity);
    }

    @Transactional
    @Override
    public LessonDTO updateModule(UUID id, LessonDTO dto) {

        log.debug("Update ModuleDTO received {}", dto.getModule());

        Optional<Lesson> obj = repository.findById(id);
        Lesson entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto, false);
        entity.getModule().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Update Module saved {}", entity.getModule());
        log.info("Module updated successfully Id: {}", entity.getModule().getId());

        return new LessonDTO(entity);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {

        log.debug("DeleteById received Id: {}", id);

        Optional<Lesson> obj = repository.findById(id);
        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        repository.deleteById(id);

        log.debug("DeleteById Lesson deleted Id: {}", id);
        log.info("Lesson deleted successfully Id: {}", id);
    }

    void copyDtoToEntity(Lesson entity, LessonDTO dto, boolean associateModule) {

        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getVideoUrl() != null) {
            entity.setVideoUrl(dto.getVideoUrl());
        }

        if (dto.getModule() != null) {
            if (entity.getModule() == null) {
                entity.setModule(new Module());
            }

            if (dto.getModule().getTitle() != null) {
                entity.getModule().setTitle(dto.getModule().getTitle());
            }
            if (dto.getModule().getDescription() != null) {
                entity.getModule().setDescription(dto.getModule().getDescription());
            }
        }

        if (associateModule && dto.getModule() != null && dto.getModule().getId() != null) {
            Optional<Module> obj = moduleRepository.findById(dto.getModule().getId());
            Module module = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getModule().getId()));
            entity.setModule(module);
            module.getLessons().add(entity);
        }
    }
}

