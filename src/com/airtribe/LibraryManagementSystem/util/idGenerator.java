package com.airtribe.LibraryManagementSystem.util;

public class idGenerator {
    private static int patronIdCounter = 0;
    private static int adminIdCounter = 0;
    private static int branchIdCounter = 0;
    private static int bookIdCounter = 0;
    private static int lendingIdCounter = 0;
    private static int reservationIdCounter = 0;

    public static int generatePatronId() {
        return patronIdCounter++;
    }

    public static String generateAdminId() {
        return "A" + adminIdCounter++;
    }

    public static String generateBranchId() { return "B" + branchIdCounter++;  }

    public static String generateBookId() {
        return "BK" + bookIdCounter++;
    }

    public static String generateLendingId() {
        return "LD" + lendingIdCounter++;
    }

    public static String generateReservationId() {
        return "RES" + reservationIdCounter++;
    }
}
