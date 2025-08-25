package com.ead.course.services;

import com.ead.course.dtos.UserDTO;
import com.ead.course.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable);
}
