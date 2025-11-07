package com.ead.course.services;

import com.ead.course.dtos.UserDTO;
import com.ead.course.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAllPaged(Pageable pageable);

    Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable);

    UserDTO insert(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    void deleteById(UUID id);
}
