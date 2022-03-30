package io.github.daniil547.common.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeExceptionWithPayload {
    public static final String MESSAGE = "Entity with such ID was not found";

    public EntityNotFoundException(UUID payload) {
        super(MESSAGE, payload);
    }

    public EntityNotFoundException(Throwable cause, UUID payload) {
        super(MESSAGE, cause, payload);
    }

    public EntityNotFoundException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, UUID payload) {
        super(MESSAGE, cause, enableSuppression, writableStackTrace, payload);
    }

    @Override
    public UUID getPayload() {
        return (UUID) super.getPayload();
    }
}
