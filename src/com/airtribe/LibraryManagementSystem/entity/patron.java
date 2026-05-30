package com.airtribe.LibraryManagementSystem.entity;

import com.airtribe.LibraryManagementSystem.enums.LendingStatus;
import com.airtribe.LibraryManagementSystem.util.idGenerator;

import java.util.*;

public class patron extends person {
    private int id;
    private List<bookLending> borrowingHistory;
    private Set<String> preferences;
    private Date membershipDate;

    public patron(String name, String contact, String address, String email) {
        super(name, contact, address, email);
        this.id = idGenerator.generatePatronId();
        this.borrowingHistory = new ArrayList<>();
        this.preferences = new HashSet<>();
        this.membershipDate = new Date();
    }

    public int getId() {
        return id;
    }

    public List<bookLending> getBorrowingHistory() {
        return new ArrayList<>(borrowingHistory);
    }

    public void addToBorrowingHistory(bookLending lending) {
        borrowingHistory.add(lending);
    }

    public Set<String> getPreferences() {
        return new HashSet<>(preferences);
    }

    public void addPreference(String genre) {
        preferences.add(genre);
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public List<bookLending> getActiveLendings() {
        List<bookLending> activeLendings = new ArrayList<>();
        for (bookLending lending : borrowingHistory) {
            if (lending.getStatus().equals(LendingStatus.ACTIVE)) {
                activeLendings.add(lending);
            }
        }
        return activeLendings;
    }
}
