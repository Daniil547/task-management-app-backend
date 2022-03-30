package io.github.daniil547.common.controllers;

import io.github.daniil547.common.exceptions.EntityNotFoundException;
import io.github.daniil547.common.exceptions.MalformedRestSearchQueryException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error<?>>> onJavaxValidationFailure(MethodArgumentNotValidException exc) {
        List<Error<?>> result = new ArrayList<>();

        exc.getBindingResult()
           .getFieldErrors()
           .forEach(e -> result.add(
                            new Error<>(e.getDefaultMessage(), //despite the name it's actually just a messag; messages you specify in javax annotations are also injected in that field
                                        e.getField().substring(e.getField().indexOf('.') + 1), //don't include dto's name
                                        e.getRejectedValue())
                    )
           );

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MalformedRestSearchQueryException.class)
    public ResponseEntity<String> onBadMalformedRestSearchQuery(MalformedRestSearchQueryException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @Getter
    @AllArgsConstructor
    public static class Error<B> {
        private final String constraint;
        private final String property;
        private final B value;
    }
}
