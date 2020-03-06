package com.projects.servicebroker.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestValidationException extends Exception {
    public <T> RequestValidationException(Set<ConstraintViolation<T>> violations) {
        super(violations.stream().map(
                ConstraintViolation::getMessage)
                .collect(Collectors.joining(System.lineSeparator())));
    }
}
