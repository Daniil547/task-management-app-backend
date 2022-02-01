package io.github.daniil547.common.exceptions;

import lombok.Getter;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Entity with such ID was not found";
    @Getter
    private final UUID badId;

    public EntityNotFoundException(UUID badId) {
        super(MESSAGE);
        this.badId = badId;
    }

    public EntityNotFoundException(Throwable cause, UUID badId) {
        super(MESSAGE, cause);
        this.badId = badId;
    }
}
