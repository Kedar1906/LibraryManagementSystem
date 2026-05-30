package com.airtribe.LibraryManagementSystem.entity;

import com.airtribe.LibraryManagementSystem.util.idGenerator;

public class libraryBranch {
    private String id;
    private String name;
    private String location;
    private String contactInfo;
    private admin admin;

    public libraryBranch(String name, String location, String contactInfo, admin admin) {
        this.id = idGenerator.generateBranchId();
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.admin = admin;
    }

    public String getBranchId() {
        return id;
    }

    public String getBranchName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public admin getAdmin() {
        return admin;
    }

    public void setAdmin(admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
