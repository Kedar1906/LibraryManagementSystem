package com.airtribe.LibraryManagementSystem.exceptions;

/**
 * Exception thrown when an operation violates business logic rules.
 * Examples: returning a book that's not checked out, reserving an available book, etc.
 */
public class OperationNotAllowedException extends Exception {
    
    public OperationNotAllowedException(String message) {
        super(message);
    }
    
    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
    

    public OperationNotAllowedException(String operationName, String reason) {
        super("Operation '" + operationName + "' is not allowed: " + reason);
    }
}
