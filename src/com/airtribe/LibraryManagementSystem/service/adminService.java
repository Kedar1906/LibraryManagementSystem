package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.entity.admin;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.repository.adminRepository;

import java.util.List;
import java.util.logging.Logger;

public class adminService {
    private adminRepository adminRepository;
    private static final Logger LOGGER = Logger.getLogger(adminService.class.getName());

    public adminService(adminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void addAdmin(String name, String contact, String address, String email) throws invalidArgumentException {
        if (name == null || name.isEmpty()) {
            throw new invalidArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new invalidArgumentException("Email cannot be null or empty");
        }

        admin newAdmin = new admin(name, contact, address, email);
        adminRepository.addAdmin(newAdmin);
        LOGGER.info("Admin added: " + name);
    }

    public void removeAdmin(String adminId) throws EntityNotFoundException, invalidArgumentException {
        admin a = getAdmin(adminId);
        if (a == null) {
            throw new EntityNotFoundException("Admin", adminId);
        }
        adminRepository.removeAdmin(adminId);
        LOGGER.info("Admin removed: " + adminId);
    }

    public admin getAdmin(String adminId) throws EntityNotFoundException, invalidArgumentException {
        admin a = adminRepository.getAdminById(adminId);
        if (a == null) {
            throw new EntityNotFoundException("Admin", adminId);
        }
        return a;
    }

    public List<admin> getAllAdmins() {
        return adminRepository.getAllAdmins();
    }

    public List<admin> searchByName(String name) throws invalidArgumentException {
        List<admin> results = adminRepository.searchByName(name);
        LOGGER.info("Search by name: " + name + " found " + results.size() + " results");
        return results;
    }
}
