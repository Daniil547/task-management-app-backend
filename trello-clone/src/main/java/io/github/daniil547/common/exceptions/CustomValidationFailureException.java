package io.github.daniil547.common.exceptions;

import lombok.Getter;

public class CustomValidationFailureException extends RuntimeExceptionWithPayload {
    @Getter
    private final String field;

    public CustomValidationFailureException(String message, String field, Object payload) {
        super(message, payload);
        this.field = field;
    }

    public CustomValidationFailureException(String message, Object payload, String field, Throwable cause) {
        super(message, cause, payload);
        this.field = field;
    }

    public CustomValidationFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object payload, String field) {
        super(message, cause, enableSuppression, writableStackTrace, payload);
        this.field = field;
    }
}
