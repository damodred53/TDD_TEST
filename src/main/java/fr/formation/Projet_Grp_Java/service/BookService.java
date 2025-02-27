package fr.formation.Projet_Grp_Java.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.repo.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}
