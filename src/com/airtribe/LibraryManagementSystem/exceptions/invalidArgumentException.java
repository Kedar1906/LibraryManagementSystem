package com.airtribe.LibraryManagementSystem.exceptions;

public class invalidArgumentException extends Exception {
    public invalidArgumentException(String message) {
        super(message);
    }

    public invalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
