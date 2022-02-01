package io.github.daniil547.common.controllers;

import io.github.daniil547.common.exceptions.EntityNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error<UUID>> onNotFound(EntityNotFoundException exc, HttpServletRequest request) {
        return new ResponseEntity<>(new Error<>(exc.getMessage(), exc.getBadId()), HttpStatus.NOT_FOUND);
    }


    @Getter
    public static class Error<B> {
        private final String message;
        private final B body;

        public Error(String message, B body) {
            this.message = message;
            this.body = body;
        }
    }
}
