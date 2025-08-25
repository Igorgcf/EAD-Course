package com.ead.course.services.impl;

import com.ead.course.dtos.UserDTO;
import com.ead.course.models.User;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable) {

        Page<User> page = repository.findAll(spec, pageable);
        return page.map(UserDTO::new);
    }
}
