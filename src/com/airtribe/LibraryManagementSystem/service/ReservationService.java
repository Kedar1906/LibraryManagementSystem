package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.entity.Reservation;
import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.patron;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.ReservationException;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.repository.ReservationRepository;

import java.util.*;
import java.util.logging.Logger;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private static final Logger LOGGER = Logger.getLogger(ReservationService.class.getName());

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserveBook(patron patron, book book) throws invalidArgumentException {
        if (patron == null) {
            throw new invalidArgumentException("Patron cannot be null");
        }
        if (book == null) {
            throw new invalidArgumentException("Book cannot be null");
        }

        Reservation reservation = new Reservation(patron, book);
        reservationRepository.addReservation(reservation);
        LOGGER.info("Book reserved: " + book.getTitle() + " by patron " + patron.getName());
        return reservation;
    }

    public void cancelReservation(String reservationId) throws EntityNotFoundException, invalidArgumentException {
        if (!reservationRepository.removeReservation(reservationId)) {
            throw new EntityNotFoundException("Reservation", reservationId);
        }
        LOGGER.info("Reservation cancelled: " + reservationId);
    }

    public List<Reservation> getPatronReservations(int patronId) {
        return reservationRepository.getReservationsByPatronId(patronId);
    }

    public List<Reservation> getBookReservations(String bookId) {
        return reservationRepository.getReservationsByBookId(bookId);
    }

    public void notifyPatronAboutAvailableBook(String bookId) throws EntityNotFoundException {
        List<Reservation> reservations = reservationRepository.getReservationsByBookId(bookId);
        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("No reservations found for book", bookId);
        }

        // Notify first person in queue (Observer Pattern Implementation)
        Reservation firstReservation = reservations.get(0);
        firstReservation.setNotified(true);
        LOGGER.info("Notification sent to patron " + firstReservation.getPatron().getName() + 
                    " about available book: " + firstReservation.getBook().getTitle());
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public Reservation getReservation(String reservationId) throws EntityNotFoundException, invalidArgumentException {
        Reservation r = reservationRepository.getReservationById(reservationId);
        if (r == null) {
            throw new EntityNotFoundException("Reservation", reservationId);
        }
        return r;
    }
}
