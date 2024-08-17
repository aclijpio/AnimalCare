package com.github.aclijpio.animalcare.controllers;

import com.github.aclijpio.animalcare.exceptions.action.ActionCreationException;
import com.github.aclijpio.animalcare.exceptions.action.ActionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ActionCreationException.class)
    public ResponseEntity<String> handleActionCreationException(ActionCreationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(ActionNotFoundException.class)
    public ResponseEntity<String> handleActionNotFoundException(ActionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
