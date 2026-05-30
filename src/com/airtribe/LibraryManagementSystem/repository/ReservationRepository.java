package com.airtribe.LibraryManagementSystem.repository;

import com.airtribe.LibraryManagementSystem.entity.Reservation;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private List<Reservation> reservations;

    public ReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) throws invalidArgumentException {
        if (reservation == null) {
            throw new invalidArgumentException("Reservation cannot be null");
        }
        reservations.add(reservation);
    }

    public boolean removeReservation(String reservationId) throws invalidArgumentException {
        if (reservationId == null || reservationId.isEmpty()) {
            throw new invalidArgumentException("Reservation ID cannot be null or empty");
        }
        return reservations.removeIf(r -> r.getId().equals(reservationId));
    }

    public Reservation getReservationById(String reservationId) throws invalidArgumentException {
        if (reservationId == null || reservationId.isEmpty()) {
            throw new invalidArgumentException("Reservation ID cannot be null or empty");
        }
        return reservations.stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    public List<Reservation> getReservationsByPatronId(int patronId) {
        List<Reservation> results = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getPatron().getId() == patronId) {
                results.add(r);
            }
        }
        return results;
    }

    public List<Reservation> getReservationsByBookId(String bookId) {
        List<Reservation> results = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getBook().getId().equals(bookId)) {
                results.add(r);
            }
        }
        return results;
    }

    public void updateReservation(Reservation reservation) throws invalidArgumentException {
        if (reservation == null) {
            throw new invalidArgumentException("Reservation cannot be null");
        }
    }
}
