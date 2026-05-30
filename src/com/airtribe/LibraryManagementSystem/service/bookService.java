package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.design.BookFactory;
import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.libraryBranch;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.exceptions.DuplicateISBNException;
import com.airtribe.LibraryManagementSystem.repository.bookRepository;

import java.util.List;
import java.util.logging.Logger;

public class bookService {
    private bookRepository bookRepository;
    private static final Logger LOGGER = Logger.getLogger(bookService.class.getName());

    public bookService(bookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String author, String isbn, int publicationYear, libraryBranch branch, int totalCopies) throws invalidArgumentException, DuplicateISBNException {
        if (title == null || title.isEmpty()) {
            throw new invalidArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.isEmpty()) {
            throw new invalidArgumentException("Author cannot be null or empty");
        }
        if (isbn == null || isbn.isEmpty()) {
            throw new invalidArgumentException("ISBN cannot be null or empty");
        }
        if (publicationYear < 0) {
            throw new invalidArgumentException("Publication year cannot be negative");
        }
        if (totalCopies <= 0) {
            throw new invalidArgumentException("Total copies must be greater than 0");
        }

        book newBook = BookFactory.createBook(title, author, isbn, publicationYear, branch, totalCopies);
        bookRepository.addBook(newBook);
        LOGGER.info("Book added: " + title + " by " + author);
    }

    public void removeBook(String bookId) throws EntityNotFoundException, invalidArgumentException {
        if (!bookRepository.removeBook(bookId)) {
            throw new EntityNotFoundException("Book", bookId);
        }
        LOGGER.info("Book removed: " + bookId);
    }

    public book getBook(String bookId) throws EntityNotFoundException, invalidArgumentException {
        book b = bookRepository.getBookById(bookId);
        if (b == null) {
            throw new EntityNotFoundException("Book", bookId);
        }
        return b;
    }

    public List<book> searchByTitle(String title) throws invalidArgumentException {
        List<book> results = bookRepository.searchByTitle(title);
        LOGGER.info("Search by title: " + title + " found " + results.size() + " results");
        return results;
    }

    public List<book> searchByAuthor(String author) throws invalidArgumentException {
        List<book> results = bookRepository.searchByAuthor(author);
        LOGGER.info("Search by author: " + author + " found " + results.size() + " results");
        return results;
    }

    public List<book> searchByISBN(String isbn) throws invalidArgumentException {
        List<book> results = bookRepository.searchByISBN(isbn);
        LOGGER.info("Search by ISBN: " + isbn + " found " + results.size() + " results");
        return results;
    }

    public List<book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public void updateBook(String bookId, String title, String author, String isbn, int publicationYear) throws EntityNotFoundException, invalidArgumentException {
        book b = bookRepository.getBookById(bookId);
        if (b == null) {
            throw new EntityNotFoundException("Book", bookId);
        }
        if (title != null && !title.isEmpty()) {
            b.setTitle(title);
        }
        if (author != null && !author.isEmpty()) {
            b.setAuthor(author);
        }
        if (isbn != null && !isbn.isEmpty()) {
            b.setIsbn(isbn);
        }
        if (publicationYear >= 0) {
            b.setPublicationYear(publicationYear);
        }
        LOGGER.info("Book updated: " + bookId);
    }

    public List<book> getBooksByBranch(libraryBranch branch) {
        return bookRepository.getBooksByBranch(branch);
    }
}
