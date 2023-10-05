package com.example.task.controller;

import com.example.task.util.model.ErrorResponse;
import com.example.task.util.EventNotAddedException;
import com.example.task.util.FilterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final String FILTER_NOT_FOUND = "Filter was not found";

    private static final String VALIDATION_ERROR = "Request validation error occurred";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(FilterNotFoundException e) {
        final ErrorResponse response = new ErrorResponse(
                FILTER_NOT_FOUND, Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EventNotAddedException.class)
    private ResponseEntity<ErrorResponse> handleException(EventNotAddedException e) {
        final List<String> errorList = new ArrayList<>();

        e.getErrors().forEach(error -> errorList.add(error.getField()));

        final ErrorResponse response = new ErrorResponse(
                VALIDATION_ERROR, errorList,
                HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
