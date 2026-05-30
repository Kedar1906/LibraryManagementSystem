package com.airtribe.LibraryManagementSystem.exceptions;

/**
 * Exception thrown when an entity (Book, Patron, Admin, Branch, Lending, Reservation) is not found.
 */
public class EntityNotFoundException extends Exception {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    

    public EntityNotFoundException(String entityType, String entityId) {
        super(entityType + " with ID '" + entityId + "' not found");
    }

}
