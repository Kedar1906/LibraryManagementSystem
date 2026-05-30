package com.airtribe.LibraryManagementSystem.repository;

import com.airtribe.LibraryManagementSystem.entity.libraryBranch;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class libraryBranchRepository {
    private List<libraryBranch> branches;

    public libraryBranchRepository() {
        this.branches = new ArrayList<>();
    }

    public void addBranch(libraryBranch branch) throws invalidArgumentException {
        if (branch == null) {
            throw new invalidArgumentException("Branch cannot be null");
        }
        branches.add(branch);
    }

    public boolean removeBranch(String branchId) throws invalidArgumentException {
        if (branchId == null || branchId.isEmpty()) {
            throw new invalidArgumentException("Branch ID cannot be null or empty");
        }
        return branches.removeIf(b -> b.getBranchId().equals(branchId));
    }

    public libraryBranch getBranchById(String branchId) throws invalidArgumentException {
        if (branchId == null || branchId.isEmpty()) {
            throw new invalidArgumentException("Branch ID cannot be null or empty");
        }
        return branches.stream()
                .filter(b -> b.getBranchId().equals(branchId))
                .findFirst()
                .orElse(null);
    }

    public List<libraryBranch> getAllBranches() {
        return new ArrayList<>(branches);
    }

    public List<libraryBranch> searchByName(String name) throws invalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new invalidArgumentException("Name cannot be null or empty");
        }
        List<libraryBranch> results = new ArrayList<>();
        for (libraryBranch b : branches) {
            if (b.getBranchName().toLowerCase().contains(name.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    public List<libraryBranch> searchByLocation(String location) throws invalidArgumentException {
        if (location == null || location.isEmpty()) {
            throw new invalidArgumentException("Location cannot be null or empty");
        }
        List<libraryBranch> results = new ArrayList<>();
        for (libraryBranch b : branches) {
            if (b.getLocation().toLowerCase().contains(location.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    public void updateBranch(libraryBranch branch) throws invalidArgumentException {
        if (branch == null) {
            throw new invalidArgumentException("Branch cannot be null");
        }
        libraryBranch existing = getBranchById(branch.getBranchId());
        if (existing == null) {
            throw new invalidArgumentException("Branch with ID " + branch.getBranchId() + " not found");
        }
        existing.setName(branch.getBranchName());
        existing.setLocation(branch.getLocation());
        existing.setContactInfo(branch.getContactInfo());
    }
}



