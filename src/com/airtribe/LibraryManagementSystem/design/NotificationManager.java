package com.airtribe.LibraryManagementSystem.design;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject: Notification Manager - manages observers and sends notifications
 */
public class NotificationManager {
    private List<NotificationObserver> observers;

    public NotificationManager() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(NotificationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }
}
