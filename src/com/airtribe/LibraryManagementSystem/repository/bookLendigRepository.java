package com.airtribe.LibraryManagementSystem.repository;

import com.airtribe.LibraryManagementSystem.entity.bookLending;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class bookLendigRepository {
    private List<bookLending> lendings;

    public bookLendigRepository() {
        this.lendings = new ArrayList<>();
    }

    public void addLending(bookLending lending) throws invalidArgumentException {
        if (lending == null) {
            throw new invalidArgumentException("Lending record cannot be null");
        }
        lendings.add(lending);
    }

    public bookLending getLendingById(String lendingId) throws invalidArgumentException {
        if (lendingId == null || lendingId.isEmpty()) {
            throw new invalidArgumentException("Lending ID cannot be null or empty");
        }
        return lendings.stream()
                .filter(l -> l.getId().equals(lendingId))
                .findFirst()
                .orElse(null);
    }

    public List<bookLending> getAllLendings() {
        return new ArrayList<>(lendings);
    }

    public List<bookLending> getLendingsByPatronId(int patronId) {
        List<bookLending> results = new ArrayList<>();
        for (bookLending l : lendings) {
            if (l.getPatron().getId() == patronId) {
                results.add(l);
            }
        }
        return results;
    }

    public List<bookLending> getActiveLendings() {
        List<bookLending> results = new ArrayList<>();
        for (bookLending l : lendings) {
            if (l.getStatus().toString().equals("ACTIVE")) {
                results.add(l);
            }
        }
        return results;
    }

    public List<bookLending> getOverdueLendings() {
        List<bookLending> results = new ArrayList<>();
        for (bookLending l : lendings) {
            if (l.isOverdue()) {
                results.add(l);
            }
        }
        return results;
    }

    public void updateLending(bookLending lending) throws invalidArgumentException {
        if (lending == null) {
            throw new invalidArgumentException("Lending cannot be null");
        }
    }
}
