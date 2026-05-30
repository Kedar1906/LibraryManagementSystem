package com.airtribe.LibraryManagementSystem.ui;

import com.airtribe.LibraryManagementSystem.design.*;
import com.airtribe.LibraryManagementSystem.entity.*;
import com.airtribe.LibraryManagementSystem.exceptions.*;
import com.airtribe.LibraryManagementSystem.repository.*;
import com.airtribe.LibraryManagementSystem.service.*;

import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    // Repositories
    private static bookRepository bookRepo;
    private static patronRepository patronRepo;
    private static adminRepository adminRepo;
    private static libraryBranchRepository branchRepo;
    private static bookLendigRepository lendingRepo;
    private static ReservationRepository reservationRepo;
    
    // Services
    private static bookService bookSvc;
    private static patronService patronSvc;
    private static adminService adminSvc;
    private static libraryBranchService branchSvc;
    private static bookLendingService lendingSvc;
    private static ReservationService reservationSvc;
    private static RecommendationService recommendationSvc;
    
    // Design Pattern
    private static NotificationManager notificationManager;
    
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            initializeSystem();
            displayMainMenu();
        } catch (invalidArgumentException e) {
            System.err.println("Error during initialization: " + e.getMessage());
            LOGGER.severe("Initialization error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            LOGGER.severe("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeSystem() throws invalidArgumentException {
        try {
            scanner = new Scanner(System.in);
            
            // Initialize repositories
            bookRepo = new bookRepository();
            patronRepo = new patronRepository();
            adminRepo = new adminRepository();
            branchRepo = new libraryBranchRepository();
            lendingRepo = new bookLendigRepository();
            reservationRepo = new ReservationRepository();
            
            // Initialize services
            bookSvc = new bookService(bookRepo);
            patronSvc = new patronService(patronRepo);
            adminSvc = new adminService(adminRepo);
            branchSvc = new libraryBranchService(branchRepo);
            lendingSvc = new bookLendingService(lendingRepo);
            reservationSvc = new ReservationService(reservationRepo);
            recommendationSvc = new RecommendationService(bookSvc, patronSvc);
            
            // Initialize design patterns
            notificationManager = new NotificationManager();
            
            // Create sample data
            createSampleData();
            
            LOGGER.info("Library Management System initialized successfully");
        } catch (Exception e) {
            LOGGER.severe("Error during system initialization: " + e.getMessage());
            throw new invalidArgumentException("Failed to initialize system: " + e.getMessage(), e);
        }
    }

    private static void createSampleData() throws invalidArgumentException {
        try {
            // Create admin and branches
            admin admin1 = new admin("John Manager", "555-0001", "123 Main St", "john@library.com");
            adminSvc.addAdmin("John Manager", "555-0001", "123 Main St", "john@library.com");
            
            libraryBranch branch1 = new libraryBranch("Downtown Branch", "123 Main St", "555-1000", admin1);
            branchRepo.addBranch(branch1);
            
            // Create sample books
            bookSvc.addBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", 1925, branch1, 5);
            bookSvc.addBook("To Kill a Mockingbird", "Harper Lee", "978-0061120084", 1960, branch1, 3);
            bookSvc.addBook("1984", "George Orwell", "978-0451524935", 1949, branch1, 4);
            
            // Create sample patrons
            patronSvc.addPatron("Alice Johnson", "555-1001", "456 Oak Ave", "alice@email.com");
            patronSvc.addPatron("Bob Smith", "555-1002", "789 Pine Rd", "bob@email.com");
            
            LOGGER.info("Sample data created successfully");
        } catch (invalidArgumentException e) {
            LOGGER.warning("Error creating sample data: " + e.getMessage());
        } catch (DuplicateISBNException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayMainMenu() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==========");
                System.out.println("1. Book Management");
                System.out.println("2. Patron Management");
                System.out.println("3. Lending Management");
                System.out.println("4. Reservation Management");
                System.out.println("5. Branch Management");
                System.out.println("6. Recommendations");
                System.out.println("7. Admin Management");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        bookManagementMenu();
                        break;
                    case "2":
                        patronManagementMenu();
                        break;
                    case "3":
                        lendingManagementMenu();
                        break;
                    case "4":
                        reservationManagementMenu();
                        break;
                    case "5":
                        branchManagementMenu();
                        break;
                    case "6":
                        recommendationMenu();
                        break;
                    case "7":
                        adminManagementMenu();
                        break;
                    case "8":
                        running = false;
                        System.out.println("Thank you for using Library Management System!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in main menu: " + e.getMessage());
                LOGGER.warning("Error in main menu: " + e.getMessage());
            }
        }
    }

    private static void bookManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            try {
                System.out.println("\n--- Book Management ---");
                System.out.println("1. Add Book");
                System.out.println("2. Search Books");
                System.out.println("3. View All Books");
                System.out.println("4. Update Book");
                System.out.println("5. Remove Book");
                System.out.println("6. Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        searchBooks();
                        break;
                    case "3":
                        viewAllBooks();
                        break;
                    case "4":
                        updateBook();
                        break;
                    case "5":
                        removeBook();
                        break;
                    case "6":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in book management: " + e.getMessage());
                LOGGER.warning("Error in book management: " + e.getMessage());
            }
        }
    }

    private static void addBook() {
        try {
            System.out.println("\n--- Add Book ---");
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();
            
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            
            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine();
            
            System.out.print("Enter publication year: ");
            int year = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter number of copies: ");
            int copies = Integer.parseInt(scanner.nextLine());
            
            List<libraryBranch> branches = branchRepo.getAllBranches();
            if (branches.isEmpty()) {
                System.out.println("No branches available. Please create a branch first.");
                return;
            }
            
            System.out.println("Available branches:");
            for (int i = 0; i < branches.size(); i++) {
                System.out.println((i + 1) + ". " + branches.get(i).getBranchName());
            }
            System.out.print("Select branch: ");
            int branchChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (branchChoice < 0 || branchChoice >= branches.size()) {
                System.out.println("Invalid branch selection.");
                return;
            }
            
            libraryBranch selectedBranch = branches.get(branchChoice);
            bookSvc.addBook(title, author, isbn, year, selectedBranch, copies);
            System.out.println("Book added successfully!");
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter valid numbers for year and copies.");
            LOGGER.warning("NumberFormatException in addBook: " + e.getMessage());
        } catch (DuplicateISBNException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Duplicate ISBN error in addBook: " + e.getMessage());
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in addBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
            LOGGER.warning("Error in addBook: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        try {
            System.out.println("\n--- Search Books ---");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by ISBN");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine().trim();
            List<book> results = new ArrayList<>();
            
            switch (choice) {
                case "1":
                    System.out.print("Enter title: ");
                    results = bookSvc.searchByTitle(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Enter author: ");
                    results = bookSvc.searchByAuthor(scanner.nextLine());
                    break;
                case "3":
                    System.out.print("Enter ISBN: ");
                    results = bookSvc.searchByISBN(scanner.nextLine());
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            if (results.isEmpty()) {
                System.out.println("No books found.");
            } else {
                displayBooks(results);
            }
        } catch (invalidArgumentException e) {
            System.err.println("Search error: " + e.getMessage());
            LOGGER.warning("Validation error in searchBooks: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error searching books: " + e.getMessage());
            LOGGER.warning("Error in searchBooks: " + e.getMessage());
        }
    }

    private static void viewAllBooks() {
        List<book> allBooks = bookSvc.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            displayBooks(allBooks);
        }
    }

    private static void displayBooks(List<book> books) {
        System.out.println("\n--- Books ---");
        for (book b : books) {
            System.out.println("ID: " + b.getId() + ", Title: " + b.getTitle() + ", Author: " + b.getAuthor() +
                    ", ISBN: " + b.getIsbn() + ", Available: " + b.getAvailableCopies() + "/" + b.getTotalCopies());
        }
    }

    private static void updateBook() {
        try {
            System.out.print("Enter book ID: ");
            String bookId = scanner.nextLine();
            
            book b = bookSvc.getBook(bookId);
            System.out.println("Current book: " + b.getTitle() + " by " + b.getAuthor());
            
            System.out.print("Enter new title (or press Enter to skip): ");
            String newTitle = scanner.nextLine();
            
            System.out.print("Enter new author (or press Enter to skip): ");
            String newAuthor = scanner.nextLine();
            
            bookSvc.updateBook(bookId, 
                    newTitle.isEmpty() ? b.getTitle() : newTitle,
                    newAuthor.isEmpty() ? b.getAuthor() : newAuthor,
                    b.getIsbn(),
                    b.getPublicationYear());
            System.out.println("Book updated successfully!");
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in updateBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error updating book: " + e.getMessage());
            LOGGER.warning("Error in updateBook: " + e.getMessage());
        }
    }

    private static void removeBook() {
        try {
            System.out.print("Enter book ID to remove: ");
            String bookId = scanner.nextLine();
            bookSvc.removeBook(bookId);
            System.out.println("Book removed successfully!");
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in removeBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error removing book: " + e.getMessage());
            LOGGER.warning("Error in removeBook: " + e.getMessage());
        }
    }

    private static void patronManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            try {
                System.out.println("\n--- Patron Management ---");
                System.out.println("1. Add Patron");
                System.out.println("2. View All Patrons");
                System.out.println("3. Search Patron");
                System.out.println("4. View Patron Details");
                System.out.println("5. Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        addPatron();
                        break;
                    case "2":
                        viewAllPatrons();
                        break;
                    case "3":
                        searchPatron();
                        break;
                    case "4":
                        viewPatronDetails();
                        break;
                    case "5":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in patron management: " + e.getMessage());
                LOGGER.warning("Error in patron management: " + e.getMessage());
            }
        }
    }

    private static void addPatron() {
        try {
            System.out.println("\n--- Add Patron ---");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter contact: ");
            String contact = scanner.nextLine();
            
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
            patronSvc.addPatron(name, contact, address, email);
            System.out.println("Patron added successfully!");
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in addPatron: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding patron: " + e.getMessage());
            LOGGER.warning("Error in addPatron: " + e.getMessage());
        }
    }

    private static void viewAllPatrons() {
        List<patron> patrons = patronSvc.getAllPatrons();
        if (patrons.isEmpty()) {
            System.out.println("No patrons in the system.");
        } else {
            System.out.println("\n--- All Patrons ---");
            for (patron p : patrons) {
                System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Email: " + p.getEmail());
            }
        }
    }

    private static void searchPatron() {
        try {
            System.out.print("Enter patron name to search: ");
            String name = scanner.nextLine();
            List<patron> results = patronSvc.searchByName(name);
            
            if (results.isEmpty()) {
                System.out.println("No patrons found.");
            } else {
                System.out.println("\n--- Search Results ---");
                for (patron p : results) {
                    System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Email: " + p.getEmail());
                }
            }
        } catch (invalidArgumentException e) {
            System.err.println("Search error: " + e.getMessage());
            LOGGER.warning("Validation error in searchPatron: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error searching patrons: " + e.getMessage());
            LOGGER.warning("Error in searchPatron: " + e.getMessage());
        }
    }

    private static void viewPatronDetails() {
        try {
            System.out.print("Enter patron ID: ");
            int patronId = Integer.parseInt(scanner.nextLine());
            
            patron p = patronSvc.getPatron(patronId);
            System.out.println("\n--- Patron Details ---");
            System.out.println("Name: " + p.getName());
            System.out.println("Contact: " + p.getContact());
            System.out.println("Address: " + p.getAddress());
            System.out.println("Email: " + p.getEmail());
            System.out.println("Membership Date: " + p.getMembershipDate());
            System.out.println("Total Books Borrowed: " + p.getBorrowingHistory().size());
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid patron ID.");
            LOGGER.warning("NumberFormatException in viewPatronDetails: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in viewPatronDetails: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error retrieving patron details: " + e.getMessage());
            LOGGER.warning("Error in viewPatronDetails: " + e.getMessage());
        }
    }

    private static void lendingManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            try {
                System.out.println("\n--- Lending Management ---");
                System.out.println("1. Checkout Book");
                System.out.println("2. Return Book");
                System.out.println("3. View Active Lendings");
                System.out.println("4. View Overdue Books");
                System.out.println("5. Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        checkoutBook();
                        break;
                    case "2":
                        returnBook();
                        break;
                    case "3":
                        viewActiveLendings();
                        break;
                    case "4":
                        viewOverdueBooks();
                        break;
                    case "5":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in lending management: " + e.getMessage());
                LOGGER.warning("Error in lending management: " + e.getMessage());
            }
        }
    }

    private static void checkoutBook() {
        try {
            System.out.print("Enter patron ID: ");
            int patronId = Integer.parseInt(scanner.nextLine());
            patron p = patronSvc.getPatron(patronId);
            
            System.out.print("Enter book ID: ");
            String bookId = scanner.nextLine();
            book b = bookSvc.getBook(bookId);
            
            bookLending lending = lendingSvc.checkoutBook(p, b);
            System.out.println("Book checked out successfully! Due date: " + lending.getDueDate());
            
            // Add observer for notification
            PatronNotificationObserver observer = new PatronNotificationObserver(p);
            notificationManager.addObserver(observer);
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid patron ID.");
            LOGGER.warning("NumberFormatException in checkoutBook: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in checkoutBook: " + e.getMessage());
        } catch (BookNotAvailableException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Book not available in checkoutBook: " + e.getMessage());
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in checkoutBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during checkout: " + e.getMessage());
            LOGGER.warning("Error in checkoutBook: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter lending ID: ");
            String lendingId = scanner.nextLine();
            lendingSvc.returnBook(lendingId);
            System.out.println("Book returned successfully!");
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in returnBook: " + e.getMessage());
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in returnBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error returning book: " + e.getMessage());
            LOGGER.warning("Error in returnBook: " + e.getMessage());
        }
    }

    private static void viewActiveLendings() {
        List<bookLending> activeLendings = lendingSvc.getActiveLendings();
        if (activeLendings.isEmpty()) {
            System.out.println("No active lendings.");
        } else {
            System.out.println("\n--- Active Lendings ---");
            for (bookLending lending : activeLendings) {
                System.out.println("Lending ID: " + lending.getId() + 
                        ", Patron: " + lending.getPatron().getName() +
                        ", Book: " + lending.getBook().getTitle() +
                        ", Due: " + lending.getDueDate());
            }
        }
    }

    private static void viewOverdueBooks() {
        List<bookLending> overdueBooks = lendingSvc.getOverdueLendings();
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
        } else {
            System.out.println("\n--- Overdue Books ---");
            for (bookLending lending : overdueBooks) {
                System.out.println("Patron: " + lending.getPatron().getName() +
                        ", Book: " + lending.getBook().getTitle() +
                        ", Due: " + lending.getDueDate());
            }
        }
    }

    private static void reservationManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            try {
                System.out.println("\n--- Reservation Management ---");
                System.out.println("1. Reserve Book");
                System.out.println("2. Cancel Reservation");
                System.out.println("3. View Patron Reservations");
                System.out.println("4. Notify Available Book");
                System.out.println("5. Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        reserveBook();
                        break;
                    case "2":
                        cancelReservation();
                        break;
                    case "3":
                        viewPatronReservations();
                        break;
                    case "4":
                        notifyAvailableBook();
                        break;
                    case "5":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in reservation management: " + e.getMessage());
                LOGGER.warning("Error in reservation management: " + e.getMessage());
            }
        }
    }

    private static void reserveBook() {
        try {
            System.out.print("Enter patron ID: ");
            int patronId = Integer.parseInt(scanner.nextLine());
            patron p = patronSvc.getPatron(patronId);
            
            System.out.print("Enter book ID: ");
            String bookId = scanner.nextLine();
            book b = bookSvc.getBook(bookId);
            
            Reservation reservation = reservationSvc.reserveBook(p, b);
            System.out.println("Book reserved successfully! Reservation ID: " + reservation.getId());
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid patron ID.");
            LOGGER.warning("NumberFormatException in reserveBook: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in reserveBook: " + e.getMessage());
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in reserveBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error reserving book: " + e.getMessage());
            LOGGER.warning("Error in reserveBook: " + e.getMessage());
        }
    }

    private static void cancelReservation() {
        try {
            System.out.print("Enter reservation ID: ");
            String reservationId = scanner.nextLine();
            reservationSvc.cancelReservation(reservationId);
            System.out.println("Reservation cancelled successfully!");
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in cancelReservation: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error cancelling reservation: " + e.getMessage());
            LOGGER.warning("Error in cancelReservation: " + e.getMessage());
        }
    }

    private static void viewPatronReservations() {
        try {
            System.out.print("Enter patron ID: ");
            int patronId = Integer.parseInt(scanner.nextLine());
            List<Reservation> reservations = reservationSvc.getPatronReservations(patronId);
            
            if (reservations.isEmpty()) {
                System.out.println("No reservations for this patron.");
            } else {
                System.out.println("\n--- Patron Reservations ---");
                for (Reservation r : reservations) {
                    System.out.println("Reservation ID: " + r.getId() + 
                            ", Book: " + r.getBook().getTitle() +
                            ", Date: " + r.getReservationDate());
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid patron ID.");
            LOGGER.warning("NumberFormatException in viewPatronReservations: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error viewing reservations: " + e.getMessage());
            LOGGER.warning("Error in viewPatronReservations: " + e.getMessage());
        }
    }

    private static void notifyAvailableBook() {
        try {
            System.out.print("Enter book ID: ");
            String bookId = scanner.nextLine();
            reservationSvc.notifyPatronAboutAvailableBook(bookId);
            System.out.println("Notification sent!");
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in notifyAvailableBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
            LOGGER.warning("Error in notifyAvailableBook: " + e.getMessage());
        }
    }

    private static void branchManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            try {
                System.out.println("\n--- Branch Management ---");
                System.out.println("1. Add Branch");
                System.out.println("2. View All Branches");
                System.out.println("3. Transfer Book");
                System.out.println("4. Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        addBranch();
                        break;
                    case "2":
                        viewAllBranches();
                        break;
                    case "3":
                        transferBook();
                        break;
                    case "4":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in branch management: " + e.getMessage());
                LOGGER.warning("Error in branch management: " + e.getMessage());
            }
        }
    }

    private static void addBranch() {
        try {
            System.out.println("\n--- Add Branch ---");
            System.out.print("Enter branch name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter location: ");
            String location = scanner.nextLine();
            
            System.out.print("Enter contact info: ");
            String contactInfo = scanner.nextLine();
            
            List<admin> admins = adminSvc.getAllAdmins();
            if (admins.isEmpty()) {
                System.out.println("No admins available. Please create an admin first.");
                return;
            }
            
            admin selectedAdmin = admins.get(0);
            branchSvc.addBranch(name, location, contactInfo, selectedAdmin);
            System.out.println("Branch added successfully!");
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in addBranch: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding branch: " + e.getMessage());
            LOGGER.warning("Error in addBranch: " + e.getMessage());
        }
    }

    private static void viewAllBranches() {
        List<libraryBranch> branches = branchRepo.getAllBranches();
        if (branches.isEmpty()) {
            System.out.println("No branches in the system.");
        } else {
            System.out.println("\n--- All Branches ---");
            for (libraryBranch b : branches) {
                System.out.println("ID: " + b.getBranchId() + ", Name: " + b.getBranchName() + ", Location: " + b.getLocation());
            }
        }
    }

    private static void transferBook() {
        try {
            System.out.print("Enter book ID: ");
            String bookId = scanner.nextLine();
            book b = bookSvc.getBook(bookId);
            
            List<libraryBranch> branches = branchRepo.getAllBranches();
            if (branches.size() < 2) {
                System.out.println("Need at least 2 branches to transfer books.");
                return;
            }
            
            System.out.println("Available branches:");
            for (int i = 0; i < branches.size(); i++) {
                System.out.println((i + 1) + ". " + branches.get(i).getBranchName());
            }
            System.out.print("Select destination branch: ");
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (choice < 0 || choice >= branches.size()) {
                System.out.println("Invalid branch selection.");
                return;
            }
            
            libraryBranch fromBranch = b.getBranch();
            libraryBranch toBranch = branches.get(choice);
            branchSvc.transferBook(b, fromBranch, toBranch);
            System.out.println("Book transferred successfully!");
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid branch number.");
            LOGGER.warning("NumberFormatException in transferBook: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Entity not found in transferBook: " + e.getMessage());
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in transferBook: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error transferring book: " + e.getMessage());
            LOGGER.warning("Error in transferBook: " + e.getMessage());
        }
    }

    private static void recommendationMenu() {
        try {
            System.out.print("Enter patron ID: ");
            int patronId = Integer.parseInt(scanner.nextLine());
            
            System.out.println("\n--- Recommendation Options ---");
            System.out.println("1. Personal Recommendations (based on history)");
            System.out.println("2. Collaborative Recommendations (similar patrons)");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine().trim();
            List<book> recommendations = new ArrayList<>();
            
            switch (choice) {
                case "1":
                    recommendations = recommendationSvc.getRecommendations(patronId);
                    break;
                case "2":
                    recommendations = recommendationSvc.getCollaborativeRecommendations(patronId);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            if (recommendations.isEmpty()) {
                System.out.println("No recommendations available.");
            } else {
                System.out.println("\n--- Recommended Books ---");
                displayBooks(recommendations);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Please enter a valid patron ID.");
            LOGGER.warning("NumberFormatException in recommendationMenu: " + e.getMessage());
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in recommendationMenu: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error getting recommendations: " + e.getMessage());
            LOGGER.warning("Error in recommendationMenu: " + e.getMessage());
        }
    }

    private static void adminManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            try {
                System.out.println("\n--- Admin Management ---");
                System.out.println("1. Add Admin");
                System.out.println("2. View All Admins");
                System.out.println("3. Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        addAdmin();
                        break;
                    case "2":
                        viewAllAdmins();
                        break;
                    case "3":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error in admin management: " + e.getMessage());
                LOGGER.warning("Error in admin management: " + e.getMessage());
            }
        }
    }

    private static void addAdmin() {
        try {
            System.out.println("\n--- Add Admin ---");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter contact: ");
            String contact = scanner.nextLine();
            
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
            adminSvc.addAdmin(name, contact, address, email);
            System.out.println("Admin added successfully!");
        } catch (invalidArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            LOGGER.warning("Validation error in addAdmin: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding admin: " + e.getMessage());
            LOGGER.warning("Error in addAdmin: " + e.getMessage());
        }
    }

    private static void viewAllAdmins() {
        List<admin> admins = adminSvc.getAllAdmins();
        if (admins.isEmpty()) {
            System.out.println("No admins in the system.");
        } else {
            System.out.println("\n--- All Admins ---");
            for (admin a : admins) {
                System.out.println("ID: " + a.getId() + ", Name: " + a.getName() + ", Email: " + a.getEmail());
            }
        }
    }
}