package com.airtribe.LibraryManagementSystem.repository;

import com.airtribe.LibraryManagementSystem.entity.patron;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class patronRepository {
    private List<patron> patrons;

    public patronRepository() {
        this.patrons = new ArrayList<>();
    }

    public void addPatron(patron patron) throws invalidArgumentException {
        if (patron == null) {
            throw new invalidArgumentException("Patron cannot be null");
        }
        patrons.add(patron);
    }

    public boolean removePatron(int patronId) throws invalidArgumentException {
        return patrons.removeIf(p -> p.getId() == patronId);
    }

    public patron getPatronById(int patronId) throws invalidArgumentException {
        return patrons.stream()
                .filter(p -> p.getId() == patronId)
                .findFirst()
                .orElse(null);
    }

    public List<patron> getAllPatrons() {
        return new ArrayList<>(patrons);
    }

    public List<patron> searchByName(String name) throws invalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new invalidArgumentException("Name cannot be null or empty");
        }
        List<patron> results = new ArrayList<>();
        for (patron p : patrons) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    public void updatePatron(patron patron) throws invalidArgumentException {
        if (patron == null) {
            throw new invalidArgumentException("Patron cannot be null");
        }
        patron existing = getPatronById(patron.getId());
        if (existing == null) {
            throw new invalidArgumentException("Patron with ID " + patron.getId() + " not found");
        }
        // Update patron information
    }
}
