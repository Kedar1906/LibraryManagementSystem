package com.airtribe.LibraryManagementSystem.repository;

import com.airtribe.LibraryManagementSystem.entity.admin;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class adminRepository {
    private List<admin> admins;

    public adminRepository() {
        this.admins = new ArrayList<>();
    }

    public void addAdmin(admin admin) throws invalidArgumentException {
        if (admin == null) {
            throw new invalidArgumentException("Admin cannot be null");
        }
        admins.add(admin);
    }

    public boolean removeAdmin(String adminId) throws invalidArgumentException {
        if (adminId == null || adminId.isEmpty()) {
            throw new invalidArgumentException("Admin ID cannot be null or empty");
        }
        return admins.removeIf(a -> a.getId().equals(adminId));
    }

    public admin getAdminById(String adminId) throws invalidArgumentException {
        if (adminId == null || adminId.isEmpty()) {
            throw new invalidArgumentException("Admin ID cannot be null or empty");
        }
        return admins.stream()
                .filter(a -> a.getId().equals(adminId))
                .findFirst()
                .orElse(null);
    }

    public List<admin> getAllAdmins() {
        return new ArrayList<>(admins);
    }

    public List<admin> searchByName(String name) throws invalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new invalidArgumentException("Name cannot be null or empty");
        }
        List<admin> results = new ArrayList<>();
        for (admin a : admins) {
            if (a.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(a);
            }
        }
        return results;
    }
}
