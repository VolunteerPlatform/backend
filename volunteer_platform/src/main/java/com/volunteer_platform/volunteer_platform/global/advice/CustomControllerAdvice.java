package com.volunteer_platform.volunteer_platform.global.advice;

import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public CustomResponse.MessageResponse illegalHandler(Exception e) {
        return new CustomResponse.MessageResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
