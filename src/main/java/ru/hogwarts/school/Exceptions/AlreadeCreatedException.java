package ru.hogwarts.school.Exceptions;

public class AlreadeCreatedException extends RuntimeException {
    public AlreadeCreatedException() {
    }

    public AlreadeCreatedException(String message) {
        super(message);
    }

    public AlreadeCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadeCreatedException(Throwable cause) {
        super(cause);
    }

    public AlreadeCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
