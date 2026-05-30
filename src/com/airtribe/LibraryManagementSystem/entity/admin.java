package com.airtribe.LibraryManagementSystem.entity;

import com.airtribe.LibraryManagementSystem.util.idGenerator;

public class admin extends person {
    private String id;

    public admin(String name, String contact, String address, String email) {
        super(name, contact, address, email);
        this.id = idGenerator.generateAdminId();
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String getContact() {
        return super.getContact();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }
}
