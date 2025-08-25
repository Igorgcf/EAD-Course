package com.ead.course.services.impl;

import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository courseUserRepository;
}
