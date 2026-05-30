package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.entity.patron;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.repository.patronRepository;

import java.util.List;
import java.util.logging.Logger;

public class patronService {
    private patronRepository patronRepository;
    private static final Logger LOGGER = Logger.getLogger(patronService.class.getName());

    public patronService(patronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public void addPatron(String name, String contact, String address, String email) throws invalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new invalidArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new invalidArgumentException("Email cannot be null or empty");
        }

        patron newPatron = new patron(name, contact, address, email);
        patronRepository.addPatron(newPatron);
        LOGGER.info("Patron added: " + name);
    }

    public void removePatron(int patronId) throws EntityNotFoundException, invalidArgumentException {
        patron p = getPatron(patronId);
        if (p == null) {
            throw new EntityNotFoundException("Patron", String.valueOf(patronId));
        }
        patronRepository.removePatron(patronId);
        LOGGER.info("Patron removed: " + patronId);
    }

    public patron getPatron(int patronId) throws EntityNotFoundException, invalidArgumentException {
        patron p = patronRepository.getPatronById(patronId);
        if (p == null) {
            throw new EntityNotFoundException("Patron", String.valueOf(patronId));
        }
        return p;
    }

    public List<patron> getAllPatrons() {
        return patronRepository.getAllPatrons();
    }

    public List<patron> searchByName(String name) throws invalidArgumentException {
        List<patron> results = patronRepository.searchByName(name);
        LOGGER.info("Search by name: " + name + " found " + results.size() + " results");
        return results;
    }

    public void updatePatron(int patronId, String name, String contact, String address, String email) throws invalidArgumentException, EntityNotFoundException {
        patron p = getPatron(patronId);
        if (name != null && !name.isEmpty()) {
            p.setName(name);
        }
        if (contact != null && !contact.isEmpty()) {
            p.setContact(contact);
        }
        if (address != null && !address.isEmpty()) {
            p.setAddress(address);
        }
        if (email != null && !email.isEmpty()) {
            p.setEmail(email);
        }
        LOGGER.info("Patron updated: " + patronId);
    }
}
