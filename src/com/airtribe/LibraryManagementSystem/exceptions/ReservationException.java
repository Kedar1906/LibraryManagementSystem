package com.airtribe.LibraryManagementSystem.exceptions;

/**
 * Exception thrown when reservation operations fail.
 * Examples: book already reserved, patron already has reservation for this book, etc.
 */
public class ReservationException extends Exception {
    
    public ReservationException(String message) {
        super(message);
    }
    
    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationException(String patronName, String bookTitle, boolean isDuplicate) {
        super(isDuplicate ? 
              "Patron \"" + patronName + "\" already has a reservation for \"" + bookTitle + "\"" :
              "Reservation failed for patron \"" + patronName + "\" and book \"" + bookTitle + "\"");
    }
}
