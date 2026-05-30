package com.airtribe.LibraryManagementSystem.design;

import com.airtribe.LibraryManagementSystem.entity.patron;

/**
 * Concrete Observer: Patron Notification
 */
public class PatronNotificationObserver implements NotificationObserver {
    private patron patron;

    public PatronNotificationObserver(patron patron) {
        this.patron = patron;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for " + patron.getName() + ": " + message);
    }

    public patron getPatron() {
        return patron;
    }
}
