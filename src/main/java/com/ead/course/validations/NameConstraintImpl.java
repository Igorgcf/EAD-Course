package com.ead.course.validations;

import com.ead.course.repositories.CourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NameConstraintImpl implements ConstraintValidator<NameConstraint, String>{

    @Autowired
    private CourseRepository repository;

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {

        boolean existsByName = repository.existsByName(name);
        return !existsByName;
    }
}
