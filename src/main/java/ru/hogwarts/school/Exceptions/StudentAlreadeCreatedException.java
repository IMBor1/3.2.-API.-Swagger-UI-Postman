package ru.hogwarts.school.Exceptions;

public class StudentAlreadeCreatedException extends RuntimeException {
    public StudentAlreadeCreatedException() {
    }

    public StudentAlreadeCreatedException(String message) {
        super(message);
    }

    public StudentAlreadeCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadeCreatedException(Throwable cause) {
        super(cause);
    }

    public StudentAlreadeCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
