package com.airtribe.LibraryManagementSystem.exceptions;


public class BookNotAvailableException extends Exception {
    
    public BookNotAvailableException(String message) {
        super(message);
    }
    
    public BookNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BookNotAvailableException(String bookTitle, int availableCopies) {
        super("Book \"" + bookTitle + "\" is not available. Available copies: " + availableCopies);
    }
}
