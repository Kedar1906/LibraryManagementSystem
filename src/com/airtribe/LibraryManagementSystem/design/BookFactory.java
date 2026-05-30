package com.airtribe.LibraryManagementSystem.design;

import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.libraryBranch;

/**
 * Factory Design Pattern: Creates book instances
 * This allows for flexible book creation with various configurations
 * Demonstrates extensibility for different book types (Physical, EBook, etc.)
 */
public class BookFactory {
    private static int bookCount = 0;
    private static int eBookCount = 0;

    /**
     * Creates a standard physical book
     */
    public static book createPhysicalBook(String title, String author, String isbn, 
                                          int publicationYear, libraryBranch branch, int totalCopies) {
        book newBook = new book(title, author, isbn, publicationYear, branch, totalCopies);
        bookCount++;
        return newBook;
    }

    /**
     * Creates an eBook (digital book)
     * Note: Currently uses same book entity but can be extended in future
     * to support different properties like file size, format, DRM, etc.
     */
    public static book createEBook(String title, String author, String isbn, 
                                   int publicationYear, libraryBranch branch) {
        // EBooks have unlimited copies (digital distribution)
        book eBook = new book(title, author, isbn, publicationYear, branch, Integer.MAX_VALUE);
        // Could add metadata: eBook.setFormat("PDF"); eBook.setDRM(true); etc.
        eBookCount++;
        bookCount++;
        return eBook;
    }

    /**
     * Original method - maintained for backward compatibility
     */
    public static book createBook(String title, String author, String isbn, 
                                  int publicationYear, libraryBranch branch, int totalCopies) {
        return createPhysicalBook(title, author, isbn, publicationYear, branch, totalCopies);
    }

    public static int getBookCount() {
        return bookCount;
    }

    public static int getEBookCount() {
        return eBookCount;
    }

    public static int getPhysicalBookCount() {
        return bookCount - eBookCount;
    }
}
