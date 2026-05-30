package com.airtribe.LibraryManagementSystem.entity;

import com.airtribe.LibraryManagementSystem.enums.LendingStatus;
import com.airtribe.LibraryManagementSystem.util.idGenerator;

import java.util.Date;

public class bookLending {
    private String id;
    private patron patron;
    private book book;
    private Date lendingDate;
    private Date dueDate;
    private Date returnDate;
    private LendingStatus status;

    public bookLending(patron patron, book book, Date lendingDate, Date dueDate) {
        this.id = idGenerator.generateLendingId();
        this.patron = patron;
        this.book = book;
        this.lendingDate = lendingDate;
        this.dueDate = dueDate;
        this.status = LendingStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public patron getPatron() {
        return patron;
    }

    public book getBook() {
        return book;
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public LendingStatus getStatus() {
        return status;
    }

    public void setStatus(LendingStatus status) {
        this.status = status;
    }

    public boolean isOverdue() {
        if (status == LendingStatus.ACTIVE) {
            return new Date().after(dueDate);
        }
        return false;
    }
}
