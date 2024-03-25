package ru.hogwarts.school.Exceptions;

public class MyNotFoundException extends RuntimeException {
    public MyNotFoundException() {
    }

    public MyNotFoundException(String message) {
        super(message);
    }

    public MyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyNotFoundException(Throwable cause) {
        super(cause);
    }

    public MyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
