package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.exception.InvalidIsbnCharacterException;
import fr.formation.Projet_Grp_Java.exception.InvalidIsbnLengthException;
import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.model.BookFormat;
import fr.formation.Projet_Grp_Java.repo.BookRepository;
import fr.formation.Projet_Grp_Java.service.IsbnValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

        @Autowired
        private IsbnValidator isbnValidator;

        private final BookRepository bookRepository;

        public BookController(BookRepository bookRepository) {
                System.out.println(" BookController chargé !");
                this.bookRepository = bookRepository;
        }

        @GetMapping
        public List<Book> getAllBooks() {
                return bookRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Book> getBookById(@PathVariable String id) {
                return bookRepository.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        // Partie concernant les recherches filtrées.
        @GetMapping("/search/isbn/{isbn}")
        public List<Book> getBooksByIsbn(@PathVariable String isbn) {
                return bookRepository.findByIsbn(isbn);
        }

        @GetMapping("/search/title/{title}")
        public List<Book> getBooksByTitle(@PathVariable String title) {
                return bookRepository.findByTitleContainingIgnoreCase(title);
        }

        @GetMapping("/search/author/{author}")
        public List<Book> getBooksByAuthor(@PathVariable String author) {
                List<Book> allBooks = bookRepository.findAll();
                return allBooks.stream()
                                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                                .collect(Collectors.toList());
        }

        @PostMapping
        public ResponseEntity<Book> createBook(@RequestBody Book book) {
                try {
                        if (!isbnValidator.validateIsbn(book.getIsbn())) {
                                return ResponseEntity.badRequest().body(null);
                        }
                } catch (InvalidIsbnLengthException | InvalidIsbnCharacterException e) {
                        return ResponseEntity.badRequest().body(null);
                }

                book.setTitle(mockIfNullWebService(book.getTitle()));
                book.setAuthor(mockIfNullWebService(book.getAuthor()));
                book.setPublisher(mockIfNullWebService(book.getPublisher()));
                book.setFormat(mockFormatIfNullWebService(book.getFormat()));

                return ResponseEntity.ok(bookRepository.save(book));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book updatedBook) {
                Optional<Book> existingBook = bookRepository.findById(id);

                if (existingBook.isPresent()) {
                        Book book = existingBook.get();
                        book.setIsbn(updatedBook.getIsbn());
                        book.setTitle(updatedBook.getTitle());
                        book.setAuthor(updatedBook.getAuthor());
                        book.setPublisher(updatedBook.getPublisher());
                        book.setAvailable(updatedBook.isAvailable());
                        book.setFormat(updatedBook.getFormat());

                        return ResponseEntity.ok(bookRepository.save(book));
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable String id) {
                if (bookRepository.existsById(id)) {
                        bookRepository.deleteById(id);
                        return ResponseEntity.noContent().build();
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        private String mockIfNullWebService(String value) {
                return (value == null || value.trim().isEmpty()) ? "test" : value;
        }

        private static final BookFormat BROCHE = BookFormat.BROCHE;

        private BookFormat mockFormatIfNullWebService(BookFormat value) {
                return (value == null) ? BROCHE : value;
        }
}
