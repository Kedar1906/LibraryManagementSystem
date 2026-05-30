package com.airtribe.LibraryManagementSystem.service;

import com.airtribe.LibraryManagementSystem.entity.book;
import com.airtribe.LibraryManagementSystem.entity.bookLending;
import com.airtribe.LibraryManagementSystem.entity.patron;
import com.airtribe.LibraryManagementSystem.exceptions.EntityNotFoundException;
import com.airtribe.LibraryManagementSystem.exceptions.invalidArgumentException;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RecommendationService {
    private bookService bookService;
    private patronService patronService;
    private static final Logger LOGGER = Logger.getLogger(RecommendationService.class.getName());

    public RecommendationService(bookService bookService, patronService patronService) {
        this.bookService = bookService;
        this.patronService = patronService;
    }

    /**
     * Recommend books based on patron's borrowing history and preferences
     * Strategy Pattern: Different recommendation strategies can be implemented
     */
    public List<book> getRecommendations(int patronId) throws invalidArgumentException, EntityNotFoundException {
        patron p = patronService.getPatron(patronId);
        List<bookLending> history = p.getBorrowingHistory();

        if (history.isEmpty()) {
            // Return popular books if no history
            return getAllBooksRandomly(5);
        }

        // Extract authors and build recommendations
        Map<String, Integer> authorScores = new HashMap<>();
        for (bookLending lending : history) {
            String author = lending.getBook().getAuthor();
            authorScores.put(author, authorScores.getOrDefault(author, 0) + 1);
        }

        List<book> recommendations = new ArrayList<>();
        Set<String> borrowedBookIds = history.stream()
                .map(l -> l.getBook().getId())
                .collect(Collectors.toSet());

        // Find books by frequently read authors that patron hasn't borrowed
        for (String author : authorScores.keySet()) {
            try {
                List<book> authorBooks = bookService.searchByAuthor(author);
                for (book b : authorBooks) {
                    if (!borrowedBookIds.contains(b.getId()) && recommendations.size() < 5) {
                        recommendations.add(b);
                    }
                }
            } catch (invalidArgumentException e) {
                LOGGER.warning("Error searching for books by author: " + author);
            }
        }

        LOGGER.info("Generated " + recommendations.size() + " recommendations for patron " + patronId);
        return recommendations;
    }

    /**
     * Get book recommendations based on similar patrons' reading habits
     */
    public List<book> getCollaborativeRecommendations(int patronId) throws invalidArgumentException, EntityNotFoundException {
        patron targetPatron = patronService.getPatron(patronId);
        List<patron> allPatrons = patronService.getAllPatrons();

        // Find similar patrons (those who read similar books)
        Map<patron, Integer> similarityScores = new HashMap<>();
        for (patron other : allPatrons) {
            if (other.getId()!= patronId) {
                int similarity = calculateSimilarity(targetPatron, other);
                if (similarity > 0) {
                    similarityScores.put(other, similarity);
                }
            }
        }

        // Get books from similar patrons
        List<book> recommendations = new ArrayList<>();
        Set<String> targetBooks = targetPatron.getBorrowingHistory().stream()
                .map(l -> l.getBook().getId())
                .collect(Collectors.toSet());

        for (patron similar : similarityScores.keySet()) {
            for (bookLending lending : similar.getBorrowingHistory()) {
                if (!targetBooks.contains(lending.getBook().getId()) && recommendations.size() < 5) {
                    recommendations.add(lending.getBook());
                }
            }
        }

        LOGGER.info("Generated " + recommendations.size() + " collaborative recommendations for patron " + patronId);
        return recommendations;
    }

    private int calculateSimilarity(patron patron1, patron patron2) {
        Set<String> books1 = patron1.getBorrowingHistory().stream()
                .map(l -> l.getBook().getAuthor())
                .collect(Collectors.toSet());
        Set<String> books2 = patron2.getBorrowingHistory().stream()
                .map(l -> l.getBook().getAuthor())
                .collect(Collectors.toSet());

        Set<String> intersection = new HashSet<>(books1);
        intersection.retainAll(books2);
        return intersection.size();
    }

    private List<book> getAllBooksRandomly(int count) {
        List<book> allBooks = bookService.getAllBooks();
        Collections.shuffle(allBooks);
        return allBooks.stream().limit(count).collect(Collectors.toList());
    }
}
