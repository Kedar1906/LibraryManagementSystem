package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.bookLending;
import com.airtribe.LibraryManagementSystem.entity.patron;
import com.airtribe.LibraryManagementSystem.enums.LendingStatus;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;
import com.airtribe.LibraryManagementSystem.exceptions.BookNotAvailableException;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.exceptions.OperationNotAllowedException;
import com.airtribe.LibraryManagementSystem.repository.bookLendigRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class bookLendingService {
    private bookLendigRepository lendingRepository;
    private static final Logger LOGGER = Logger.getLogger(bookLendingService.class.getName());
    private static final int LENDING_DURATION_DAYS = 14;

    public bookLendingService(bookLendigRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public bookLending checkoutBook(patron patron, book book) throws invalidArgumentException, BookNotAvailableException {
        if (patron == null) {
            throw new invalidArgumentException("Patron cannot be null");
        }
        if (book == null) {
            throw new invalidArgumentException("Book cannot be null");
        }
        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException(book.getTitle(), book.getAvailableCopies());
        }

        Date lendingDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendingDate);
        calendar.add(Calendar.DAY_OF_MONTH, LENDING_DURATION_DAYS);
        Date dueDate = calendar.getTime();

        bookLending lending = new bookLending(patron, book, lendingDate, dueDate);
        lendingRepository.addLending(lending);
        book.decreaseAvailableCopies();
        patron.addToBorrowingHistory(lending);

        LOGGER.info("Book checked out: " + book.getTitle() + " to patron " + patron.getName());
        return lending;
    }

    public void returnBook(String lendingId) throws invalidArgumentException, EntityNotFoundException {
        if (lendingId == null || lendingId.isEmpty()) {
            throw new invalidArgumentException("Lending ID cannot be null or empty");
        }

        bookLending lending = lendingRepository.getLendingById(lendingId);
        if (lending == null) {
            throw new EntityNotFoundException("Lending", lendingId);
        }

        lending.setReturnDate(new Date());
        lending.setStatus(LendingStatus.RETURNED);
        lending.getBook().increaseAvailableCopies();

        LOGGER.info("Book returned: " + lending.getBook().getTitle() + " by patron " + lending.getPatron().getName());
    }

    public List<bookLending> getPatronBorrowingHistory(int patronId) {
        return lendingRepository.getLendingsByPatronId(patronId);
    }

    public List<bookLending> getActiveLendings() {
        return lendingRepository.getActiveLendings();
    }

    public List<bookLending> getOverdueLendings() {
        return lendingRepository.getOverdueLendings();
    }

    public boolean isBookOverdue(String lendingId) throws EntityNotFoundException, invalidArgumentException {
        bookLending lending = lendingRepository.getLendingById(lendingId);
        if (lending == null) {
            throw new EntityNotFoundException("Lending", lendingId);
        }
        return lending.isOverdue();
    }

    public List<bookLending> getAllLendings() {
        return lendingRepository.getAllLendings();
    }
}
