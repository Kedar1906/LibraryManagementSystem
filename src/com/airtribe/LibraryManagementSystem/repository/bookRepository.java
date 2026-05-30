package com.airtribe.LibraryManagementSystem.repository;

import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.libraryBranch;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.DuplicateISBNException;

import java.util.*;

public class bookRepository {
    private List<book> books;

    public bookRepository() {
        this.books = new ArrayList<>();
    }

    public void addBook(book book) throws invalidArgumentException, DuplicateISBNException {
        if (book == null) {
            throw new invalidArgumentException("Book cannot be null");
        }
        book existingBook = books.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).findFirst().orElse(null);
        if (existingBook != null) {
            throw new DuplicateISBNException(book.getIsbn(), existingBook.getTitle());
        }
        books.add(book);
    }

    public boolean removeBook(String bookId) throws invalidArgumentException {
        if (bookId == null || bookId.isEmpty()) {
            throw new invalidArgumentException("Book ID cannot be null or empty");
        }
        return books.removeIf(b -> b.getId().equals(bookId));
    }

    public book getBookById(String bookId) throws invalidArgumentException {
        if (bookId == null || bookId.isEmpty()) {
            throw new invalidArgumentException("Book ID cannot be null or empty");
        }
        return books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

    public List<book> searchByTitle(String title) throws invalidArgumentException {
        if (title == null || title.isEmpty()) {
            throw new invalidArgumentException("Title cannot be null or empty");
        }
        List<book> results = new ArrayList<>();
        for (book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    public List<book> searchByAuthor(String author) throws invalidArgumentException {
        if (author == null || author.isEmpty()) {
            throw new invalidArgumentException("Author cannot be null or empty");
        }
        List<book> results = new ArrayList<>();
        for (book b : books) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    public List<book> searchByISBN(String isbn) throws invalidArgumentException {
        if (isbn == null || isbn.isEmpty()) {
            throw new invalidArgumentException("ISBN cannot be null or empty");
        }
        List<book> results = new ArrayList<>();
        for (book b : books) {
            if (b.getIsbn().equals(isbn)) {
                results.add(b);
            }
        }
        return results;
    }

    public List<book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<book> getBooksByBranch(libraryBranch branch) {
        List<book> branchBooks = new ArrayList<>();
        for (book b : books) {
            if (b.getBranchId().equals(branch.getBranchId())) {
                branchBooks.add(b);
            }
        }
        return branchBooks;
    }

    public void updateBook(book book) throws invalidArgumentException {
        if (book == null) {
            throw new invalidArgumentException("Book cannot be null");
        }
        book existing = getBookById(book.getId());
        if (existing == null) {
            throw new invalidArgumentException("Book with ID " + book.getId() + " not found");
        }
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setIsbn(book.getIsbn());
        existing.setPublicationYear(book.getPublicationYear());
    }
}
