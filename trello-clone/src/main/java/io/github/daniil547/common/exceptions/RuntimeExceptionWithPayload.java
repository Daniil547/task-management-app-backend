package io.github.daniil547.common.exceptions;

/**
 * Allows passing value, which caused exception.
 */
public class RuntimeExceptionWithPayload extends RuntimeException { //Generic exceptions are forbidden
    private final Object payload;

    public RuntimeExceptionWithPayload(Object payload) {
        this.payload = payload;
    }

    public RuntimeExceptionWithPayload(String message, Object payload) {
        super(message);
        this.payload = payload;
    }

    public RuntimeExceptionWithPayload(String message, Throwable cause, Object payload) {
        super(message, cause);
        this.payload = payload;
    }

    public RuntimeExceptionWithPayload(Throwable cause, Object payload) {
        super(cause);
        this.payload = payload;
    }

    public RuntimeExceptionWithPayload(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object payload) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

    ;
}
