package com.airtribe.LibraryManagementSystem.exceptions;

public class DuplicateISBNException extends Exception {
    
    public DuplicateISBNException(String message) {
        super(message);
    }
    
    public DuplicateISBNException(String message, Throwable cause) {
        super(message, cause);
    }
    

    public DuplicateISBNException(String isbn, String existingTitle) {
        super("A book with ISBN '" + isbn + "' already exists (Title: \"" + existingTitle + "\"). " +
              "Each book must have a unique ISBN.");
    }
}
