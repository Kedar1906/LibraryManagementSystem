package com.airtribe.LibraryManagementSystem.entity;

import com.airtribe.LibraryManagementSystem.enums.BookStatus;
import com.airtribe.LibraryManagementSystem.util.idGenerator;

public class book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private BookStatus status;
    private libraryBranch branch;
    private int totalCopies;
    private int availableCopies;

    public book(String title, String author, String isbn, int publicationYear, libraryBranch branch, int totalCopies) {
        this.id = idGenerator.generateBookId();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.branch = branch;
        this.status = BookStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public libraryBranch getBranch() {
        return branch;
    }

    public void setBranch(libraryBranch branch) {
        this.branch = branch;
    }

    public String getBranchId() {
        return branch.getBranchId();
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void decreaseAvailableCopies() {
        if (this.availableCopies > 0) {
            this.availableCopies--;
            if (this.availableCopies == 0) {
                this.status = BookStatus.CHECKED_OUT;
            }
        }
    }

    public void increaseAvailableCopies() {
        this.availableCopies++;
        if (this.status == BookStatus.CHECKED_OUT) {
            this.status = BookStatus.AVAILABLE;
        }
    }
}
