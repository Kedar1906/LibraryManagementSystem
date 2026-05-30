package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.entity.admin;
import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.libraryBranch;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.repository.libraryBranchRepository;

import java.util.List;
import java.util.logging.Logger;

public class libraryBranchService {
    private libraryBranchRepository branchRepository;
    private static final Logger LOGGER = Logger.getLogger(libraryBranchService.class.getName());

    public libraryBranchService(libraryBranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public void addBranch(String name, String location, String contactInfo, admin admin) throws invalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new invalidArgumentException("Branch name cannot be null or empty");
        }
        if (location == null || location.isEmpty()) {
            throw new invalidArgumentException("Location cannot be null or empty");
        }
        if (admin == null) {
            throw new invalidArgumentException("Admin cannot be null");
        }

        libraryBranch newBranch = new libraryBranch(name, location, contactInfo, admin);
        branchRepository.addBranch(newBranch);
        LOGGER.info("Branch added: " + name);
    }

    public void removeBranch(String branchId) throws EntityNotFoundException, invalidArgumentException {
        libraryBranch b = getBranch(branchId);
        if (b == null) {
            throw new EntityNotFoundException("Branch", branchId);
        }
        branchRepository.removeBranch(branchId);
        LOGGER.info("Branch removed: " + branchId);
    }

    public libraryBranch getBranch(String branchId) throws EntityNotFoundException, invalidArgumentException {
        libraryBranch b = branchRepository.getBranchById(branchId);
        if (b == null) {
            throw new EntityNotFoundException("Branch", branchId);
        }
        return b;
    }

    public List<libraryBranch> getAllBranches() {
        return branchRepository.getAllBranches();
    }

    public List<libraryBranch> searchByName(String name) throws invalidArgumentException {
        List<libraryBranch> results = branchRepository.searchByName(name);
        LOGGER.info("Search by name: " + name + " found " + results.size() + " results");
        return results;
    }

    public List<libraryBranch> searchByLocation(String location) throws invalidArgumentException {
        List<libraryBranch> results = branchRepository.searchByLocation(location);
        LOGGER.info("Search by location: " + location + " found " + results.size() + " results");
        return results;
    }

    public void updateBranch(String branchId, String name, String location, String contactInfo) throws EntityNotFoundException, invalidArgumentException {
        libraryBranch b = getBranch(branchId);
        if (name != null && !name.isEmpty()) {
            b.setName(name);
        }
        if (location != null && !location.isEmpty()) {
            b.setLocation(location);
        }
        if (contactInfo != null && !contactInfo.isEmpty()) {
            b.setContactInfo(contactInfo);
        }
        LOGGER.info("Branch updated: " + branchId);
    }

    public void transferBook(book book, libraryBranch fromBranch, libraryBranch toBranch) throws invalidArgumentException {
        if (book == null) {
            throw new invalidArgumentException("Book cannot be null");
        }
        if (!book.getBranchId().equals(fromBranch.getBranchId())) {
            throw new invalidArgumentException("Book is not in the source branch");
        }
        book.setBranch(toBranch);
        LOGGER.info("Book transferred: " + book.getTitle() + " from " + fromBranch.getBranchName() + " to " + toBranch.getBranchName());
    }
}
