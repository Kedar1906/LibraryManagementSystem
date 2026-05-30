package com.airtribe.LibraryManagementSystem.exceptions;

/**
 * Exception thrown when a patron exceeds the maximum number of concurrent book lendings.
 */
public class MaxLendingLimitExceededException extends Exception {
    
    public MaxLendingLimitExceededException(String message) {
        super(message);
    }
    
    public MaxLendingLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxLendingLimitExceededException(String patronName, int currentCount, int maxLimit) {
        super("Patron \"" + patronName + "\" has reached the maximum lending limit. " +
              "Current: " + currentCount + ", Maximum allowed: " + maxLimit);
    }
}
