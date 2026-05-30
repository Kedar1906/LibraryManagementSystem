# Library Management System

A comprehensive Java-based Library Management System demonstrating Object-Oriented Programming (OOP), SOLID principles, and design patterns.

## Project Structure

```
src/com/airtribe/LibraryManagementSystem/
├── entity/           # Data models
│   ├── person.java          (Abstract base class)
│   ├── patron.java          (Library member)
│   ├── admin.java           (Staff member)
│   ├── book.java            (Book entity)
│   ├── bookLending.java     (Lending transaction)
│   ├── libraryBranch.java   (Branch location)
│   └── Reservation.java     (Book reservation)
├── repository/       # Data access layer
│   ├── patronRepository.java
│   ├── bookRepository.java
│   ├── adminRepository.java
│   ├── bookLendigRepository.java
│   ├── libraryBranchRepository.java
│   └── ReservationRepository.java
├── service/         # Business logic layer
│   ├── patronService.java
│   ├── bookService.java
│   ├── adminService.java
│   ├── bookLendingService.java
│   ├── libraryBranchService.java
│   ├── ReservationService.java
│   └── RecommendationService.java
├── design/          # Design patterns
│   ├── BookFactory.java                (Factory Pattern)
│   ├── NotificationObserver.java       (Observer Pattern)
│   ├── PatronNotificationObserver.java (Concrete Observer)
│   └── NotificationManager.java        (Subject)
├── enums/           # Enumerations
│   ├── BookStatus.java  (AVAILABLE, CHECKED_OUT, RESERVED, LOST)
│   └── LendingStatus.java (ACTIVE, RETURNED, OVERDUE)
├── exceptions/      # Custom exceptions
│   └── invalidArgumentException.java
├── util/            # Utility classes
│   └── idGenerator.java (ID generation)
└── ui/              # User interface
    └── Main.java
```

## Core Features

### 1. Book Management
- Add, remove, and update books
- Search books by:
  - Title
  - Author
  - ISBN
- Track available and borrowed copies
- Manage books across multiple branches

### 2. Patron Management
- Register new library members
- Update patron information
- Track borrowing history
- View patron preferences

### 3. Lending Process
- Checkout books to patrons
- Return books
- Track lending duration (14-day default)
- Identify overdue books
- Automatic inventory management

### 4. Reservation System
- Allow patrons to reserve checked-out books
- Queue management for multiple reservations
- Notification system when books become available
- Cancel reservations

### 5. Multi-Branch Support
- Manage multiple library branches
- Transfer books between branches
- View books by branch
- Branch-specific inventory

### 6. Book Recommendations
- **Personal Recommendations**: Based on patron's borrowing history and reading patterns
- **Collaborative Recommendations**: Based on similar patrons' reading habits
- Recommendation algorithm uses efficient data structures (Maps, Sets)

### 7. Inventory Management
- Real-time tracking of book availability
- Available copies counter
- Book status management
- Automatic status updates

## Design Patterns Implemented

### 1. **Factory Pattern** (BookFactory.java)
Creates book instances with consistent configuration, allowing flexible book creation without modifying existing code.

```java
book newBook = BookFactory.createBook(title, author, isbn, year, branch, copies);
```

### 2. **Observer Pattern** (NotificationObserver, NotificationManager)
Implements a notification system where patrons are notified when reserved books become available.

```java
notificationManager.addObserver(new PatronNotificationObserver(patron));
notificationManager.notifyObservers("Your reserved book is now available!");
```

### 3. **Strategy Pattern** (RecommendationService)
Different recommendation strategies can be implemented:
- Personal recommendations based on history
- Collaborative recommendations based on similar patrons

### 4. **Repository Pattern**
Abstracts data access logic, making it easy to switch between different storage mechanisms.

## OOP Principles Applied

### Inheritance
- `person` is an abstract base class
- `patron` and `admin` extend `person`

### Encapsulation
- Private fields with public getters and setters
- Business logic encapsulated in service classes

### Polymorphism
- Abstract `person` class with concrete implementations
- Observer interface with multiple implementations

### Abstraction
- Abstract `person` class defines common attributes
- Services abstract business logic from repositories

## SOLID Principles

### Single Responsibility
- Each class has one reason to change
- Services handle business logic
- Repositories handle data access

### Open/Closed
- System is open for extension (new observers, strategies)
- Closed for modification (existing code unchanged)

### Liskov Substitution
- `patron` and `admin` can be used wherever `person` is expected

### Interface Segregation
- `NotificationObserver` interface is focused and specific

### Dependency Inversion
- Services depend on abstractions (repositories)
- High-level modules don't depend on low-level modules

## Technical Features

### Logging
- Java built-in Logger for important events
- Tracks: add/remove/update operations, searches, errors

### Exception Handling
- Custom `invalidArgumentException` for validation
- Try-catch blocks for error handling
- Input validation in all service methods

### Collections Used
- **List**: Ordered collections (books, patrons, lendings)
- **Set**: Unique elements (preferences, book IDs for checking)
- **Map**: Key-value pairs (author scores for recommendations)

### Date Management
- Automatic due date calculation (14 days from checkout)
- Overdue detection
- Date-based reservation tracking

## Usage Guide

### Starting the Application
```bash
java com.airtribe.LibraryManagementSystem.ui.Main
```

### Main Menu Options
1. **Book Management**: Add, search, view, update, remove books
2. **Patron Management**: Manage library members
3. **Lending Management**: Checkout, return, track lendings
4. **Reservation Management**: Reserve books, manage queue
5. **Branch Management**: Manage branches, transfer books
6. **Recommendations**: Get personalized book recommendations
7. **Admin Management**: Manage library staff

### Sample Operations

#### Adding a Book
1. Select "Book Management" → "Add Book"
2. Enter book details (title, author, ISBN, year, copies)
3. Select a branch
4. Book is added to inventory

#### Checking Out a Book
1. Select "Lending Management" → "Checkout Book"
2. Enter patron ID and book ID
3. System decreases available copies
4. Lending record is created with due date

#### Getting Recommendations
1. Select "Recommendations"
2. Enter patron ID
3. Choose recommendation type (personal or collaborative)
4. View recommended books based on reading history

## Enhancements Made

### Beyond Core Requirements
1. **Notification System**: Observer pattern for book availability notifications
2. **Collaborative Filtering**: Recommendation algorithm using similar patrons
3. **Comprehensive Logging**: Track all operations
4. **Multi-Strategy Recommendations**: Different recommendation algorithms
5. **Branch Transfer**: Move books between library branches
6. **Overdue Tracking**: Automatic detection and management
7. **Preference Tracking**: Track patron's genre preferences
8. **Factory Pattern**: Flexible book creation

## Error Handling

The system includes comprehensive error handling:
- Input validation for all user inputs
- Null checks for objects
- Custom exceptions for business logic violations
- Meaningful error messages for user guidance

## Future Enhancements

1. **Persistence Layer**: Database integration (SQL/NoSQL)
2. **Authentication**: User login and roles
3. **Fine Management**: Calculate and track overdue fines
4. **Member Ratings**: Book ratings and reviews
5. **Email Notifications**: Send notifications via email
6. **Web Interface**: REST API and web frontend
7. **Advanced Reporting**: Generate library statistics
8. **Machine Learning**: Enhanced recommendation algorithms

## Code Quality

- **Naming Conventions**: Clear, descriptive names
- **Code Organization**: Logical package structure
- **Documentation**: JavaDoc comments and inline documentation
- **Error Messages**: User-friendly error messages
- **Validation**: Input validation at all entry points

## Compilation and Execution

```bash
# Compile
javac -d bin src/com/airtribe/LibraryManagementSystem/**/*.java

# Run
java -cp bin com.airtribe.LibraryManagementSystem.ui.Main
```

## Logging Configuration

The system uses Java's built-in logging framework. Logs include:
- Book operations (add, remove, update)
- Patron operations
- Lending transactions
- Search operations
- System initialization
- Errors and exceptions

## Conclusion

This Library Management System demonstrates comprehensive understanding of:
- Object-Oriented Programming principles
- SOLID design principles
- Industry-standard design patterns
- Java collection frameworks
- Error handling and validation
- Professional code organization

The system is scalable, maintainable, and ready for enhancement with persistence and additional features.
