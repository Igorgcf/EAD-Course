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
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {

        Page<User> page = repository.findAll(pageable);
        return page.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable) {

        Page<User> page = repository.findAll(spec, pageable);
        return page.map(UserDTO::new);
    }

    @Override
    public UserDTO insert(UserDTO dto) {

        log.debug("UserDTO received: {} ", dto);
        User entity = dto.convertToUser();
        repository.save(entity);
        log.info("User saved successfully Id: {} ", entity.getId());

        return new UserDTO(entity);
    }
}
