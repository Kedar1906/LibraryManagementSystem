package com.airtribe.LibraryManagementSystem.entity;

import com.airtribe.LibraryManagementSystem.util.idGenerator;

import java.util.Date;

public class Reservation {
    private String id;
    private patron patron;
    private book book;
    private Date reservationDate;
    private boolean notified;

    public Reservation(patron patron, book book) {
        this.id = idGenerator.generateReservationId();
        this.patron = patron;
        this.book = book;
        this.reservationDate = new Date();
        this.notified = false;
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

    public Date getReservationDate() {
        return reservationDate;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}
