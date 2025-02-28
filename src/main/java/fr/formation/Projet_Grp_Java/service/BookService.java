package fr.formation.Projet_Grp_Java.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Service;

import fr.formation.Projet_Grp_Java.exception.InvalidIsbnCharacterException;
import fr.formation.Projet_Grp_Java.exception.InvalidIsbnLengthException;
import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.model.BookFormat;
import fr.formation.Projet_Grp_Java.repo.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final IsbnValidator isbnValidator;
    private final NewIsbnValidator newIsbnValidator;

    public BookService(BookRepository bookRepository, IsbnValidator isbnValidator, NewIsbnValidator newIsbnValidator) {
        this.bookRepository = bookRepository;
        this.isbnValidator = isbnValidator;
        this.newIsbnValidator = newIsbnValidator;
    }

    public Book createBook(@RequestBody Book book) {

        book.setIsbn(deleteBareIsbn(book.getIsbn()));

        if (book.getIsbn() != null && book.getIsbn().length() == 13) {
            try {
                if (!newIsbnValidator.validateNewIsbn(book.getIsbn())) {
                    return null;
                }
            } catch (InvalidIsbnLengthException | InvalidIsbnCharacterException e) {
                throw new IllegalArgumentException("Invalid ISBN");
            }
        } else {
            try {
                if (!isbnValidator.validateIsbn(book.getIsbn())) {
                    return null;
                }
            } catch (InvalidIsbnLengthException | InvalidIsbnCharacterException e) {
                throw new IllegalArgumentException("Invalid ISBN");
            }
        }

        book.setTitle(mockIfNullWebService(book.getTitle()));
        book.setAuthor(mockIfNullWebService(book.getAuthor()));
        book.setPublisher(mockIfNullWebService(book.getPublisher()));
        book.setFormat(mockFormatIfNullWebService(book.getFormat()));

        return bookRepository.save(book);
    }

    public List<Book> getBooksByIsbn(String isbn) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public String mockIfNullWebService(String value) {
        return (value == null || value.trim().isEmpty()) ? "test" : value;
    }

    private static final BookFormat BROCHE = BookFormat.BROCHE;

    public BookFormat mockFormatIfNullWebService(BookFormat value) {
        return (value == null) ? BROCHE : value;
    }

    public String deleteBareIsbn(String isbn) {
        return isbn.replaceAll("[^0-9X]", "");
    }
}
