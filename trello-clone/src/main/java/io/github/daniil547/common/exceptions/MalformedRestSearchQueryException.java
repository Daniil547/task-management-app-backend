package io.github.daniil547.common.exceptions;

public class MalformedRestSearchQueryException extends RuntimeException {
    public static final String MESSAGE = "Malformed search query was sent to the REST API." +
                                         " Please ensure the query matches requirements in the API specification.";

    public MalformedRestSearchQueryException() {
        super(MESSAGE);
    }

    public MalformedRestSearchQueryException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public MalformedRestSearchQueryException(String message) {
        super(message);
    }
}
