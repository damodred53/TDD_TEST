package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.repo.BookRepository;
import fr.formation.Projet_Grp_Java.service.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

        private final BookRepository bookRepository;

        private final BookService bookService;

        public BookController(BookRepository bookRepository, BookService bookService) {
                this.bookRepository = bookRepository;
                this.bookService = bookService;
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
                return bookService.getBooksByIsbn(isbn);

        }

        @GetMapping("/search/title/{title}")
        public List<Book> getBooksByTitle(@PathVariable String title) {
                return bookService.getBooksByTitle(title);
        }

        @GetMapping("/search/author/{author}")
        public List<Book> getBooksByAuthor(@PathVariable String author) {
                return bookService.getBooksByAuthor(author);
        }

        @PostMapping
        public Book createBook(@RequestBody Book book) {
                return bookService.createBook(book);
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

}
