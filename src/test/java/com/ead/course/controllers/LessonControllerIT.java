package com.ead.course.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class LessonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private LessonRepository repository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    public void findAllPagedShouldReturnAllLessonPaged() throws Exception {

        ResultActions result = mockMvc.perform(get("/lessons"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].title").exists());
        result.andExpect(jsonPath("$.content[0].description").exists());
        result.andExpect(jsonPath("$.content[0].videoUrl").exists());
        result.andExpect(jsonPath("$.content[0].creationDate").exists());
        result.andExpect(jsonPath("$.content[0].lastUpdateDate").exists());
    }

    @Test
    public void findByIdShouldReturnLessonByIdWhenIdExists() throws Exception {

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/lessons/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.videoUrl").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        Optional<Module> obj = moduleRepository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = Factory.createdLessonDto(id);

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/lessons")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.videoUrl").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenCorrectStructureAndIdExists() throws Exception {

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = Factory.createdLessonDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/lessons/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.videoUrl").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updateModuleShouldSaveModuleWhenCorrectStructureAndIdExists() throws Exception {

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        LessonDTO dto = Factory.createdLessonDtoUpdateModule();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/lessons/modules/lessons/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.module").exists());
        result.andExpect(jsonPath("$.module.id").exists());
        result.andExpect(jsonPath("$.module.title").exists());
        result.andExpect(jsonPath("$.module.description").exists());
        result.andExpect(jsonPath("$.module.creationDate").exists());
        result.andExpect(jsonPath("$.module.lastUpdateDate").exists());
    }

    @Test
    public void deleteByIdShouldDeleteLessonByIdWhenIdExists() throws Exception {

        Optional<Lesson> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/lessons/{id}", id));

        result.andExpect(status().isOk());
    }
}
