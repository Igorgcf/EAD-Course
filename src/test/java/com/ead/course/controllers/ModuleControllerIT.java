package com.ead.course.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ead.course.dtos.ModuleDTO;
import com.ead.course.models.Module;
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
public class ModuleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ModuleRepository repository;

    @Test
    public void findAllPagedShouldReturnAllModulePaged() throws Exception {

        ResultActions result = mockMvc.perform(get("/modules"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].title").exists());
        result.andExpect(jsonPath("$.content[0].description").exists());
        result.andExpect(jsonPath("$.content[0].creationDate").exists());
        result.andExpect(jsonPath("$.content[0].lastUpdateDate").exists());
    }

    @Test
    public void findByIdShouldReturnModuleByIdWhenIdExists() throws Exception {

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/modules/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void insertShouldSaveModuleWhenCorrectStructure() throws Exception {

        ModuleDTO dto = Factory.createdModuleDto();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/modules")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenCorrectStructureAndIdExists() throws Exception {

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ModuleDTO dto = Factory.createdModuleDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/modules/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void deleteByIdShouldDeleteModuleByIdWhenIdExists() throws Exception {

        Optional<Module> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/modules/{id}", id));

        result.andExpect(status().isOk());
    }
}
