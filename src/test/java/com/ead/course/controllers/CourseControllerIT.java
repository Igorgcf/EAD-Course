package com.ead.course.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.models.Course;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import com.ead.course.services.impl.CourseServiceImpl;
import com.ead.course.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
public class CourseControllerIT {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void findAllPagedShouldReturnAllCoursePaged() throws Exception {

        ResultActions result = mockMvc.perform(get("/courses"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
        result.andExpect(jsonPath("$.content[0].description").exists());
        result.andExpect(jsonPath("$.content[0].imageUrl").exists());
        result.andExpect(jsonPath("$.content[0].status").exists());
        result.andExpect(jsonPath("$.content[0].courseLevel").exists());
        result.andExpect(jsonPath("$.content[0].instructorId").exists());
        result.andExpect(jsonPath("$.content[0].creationDate").exists());
        result.andExpect(jsonPath("$.content[0].lastUpdateDate").exists());
    }

    @Test
    public void findByIdShouldReturnCourseByIdWhenIdExists() throws Exception {

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/courses/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.imageUrl").exists());
        result.andExpect(jsonPath("$.status").exists());
        result.andExpect(jsonPath("$.courseLevel").exists());
        result.andExpect(jsonPath("$.instructorId").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        CourseDTO dto = Factory.createdCourseDto();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/courses")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.imageUrl").exists());
        result.andExpect(jsonPath("$.status").exists());
        result.andExpect(jsonPath("$.courseLevel").exists());
        result.andExpect(jsonPath("$.instructorId").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenCorrectStructureAndIdExists() throws Exception {

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CourseDTO dto = Factory.createdCourseDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/courses/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.imageUrl").exists());
        result.andExpect(jsonPath("$.status").exists());
        result.andExpect(jsonPath("$.courseLevel").exists());
        result.andExpect(jsonPath("$.instructorId").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updateNameShouldSaveNameWhenCorrectStructureAndIdExists() throws Exception {

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        CourseDTO dto = Factory.createdCourseDtoToUpdateName();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/courses/{id}/name", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldDeleteCourseByIdWhenIdExists() throws Exception {

        Optional<Course> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/courses/{id}", id));

        result.andExpect(status().isOk());
    }
}
