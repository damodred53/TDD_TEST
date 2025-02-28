package fr.formation.Projet_Grp_Java.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.model.BookFormat;
import fr.formation.Projet_Grp_Java.repo.BookRepository;
import fr.formation.Projet_Grp_Java.service.BookService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    private static final BookFormat POCHE = BookFormat.POCHE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book bookTest1;
    private Book bookTest2;
    private Book bookTest3;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookTest1 = new Book("1", "2070405370", "Le Comte de Monte-Cristo", "Alexandre Dumas", "Le Livre de Poche",
                POCHE, true);
        bookTest2 = new Book("2", "2701192919", "Pauline", "Alexandre Dumas", "Le Livre de Poche", POCHE, true);
        bookTest3 = new Book("3", "2701192917", "Un mauvais livre", "Un mauvais auteur", "Un mauvais éditeur", POCHE,
                false);

        books = Arrays.asList(bookTest1, bookTest2);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookController.getAllBooks();
        assertEquals(2, result.size());
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(bookTest1));
        assertEquals(bookTest1, bookController.getBookById("1").getBody());
    }

    @Test
    void testGetBookByIsbn() {
        when(bookService.getBooksByIsbn("2070405370")).thenReturn(books);
        assertEquals(books, bookController.getBooksByIsbn("2070405370"));
    }

    @Test
    void getBooksByTitle() {
        List<Book> expectedBooks = List.of(bookTest1);
        when(bookService.getBooksByTitle("Le Comte de Monte-Cristo")).thenReturn(expectedBooks);

        List<Book> result = bookController.getBooksByTitle("Le Comte de Monte-Cristo");

        assertEquals(expectedBooks, result);
        assertEquals(1, result.size());
        assertTrue(result.contains(bookTest1));
        assertFalse(result.contains(bookTest2));
    }

    @Test
    void getBooksByAuthor() {
        List<Book> expectedBooks = List.of(bookTest1, bookTest2);
        when(bookService.getBooksByAuthor("Alexandre Dumas")).thenReturn(expectedBooks);

        List<Book> result = bookService.getBooksByAuthor("Alexandre Dumas");

        assertTrue(result.contains(bookTest1));
        assertTrue(result.contains(bookTest2));
        assertFalse(result.contains(bookTest3));
    }

    @Test
    void createBookWithCompleteData() {
        when(bookService.createBook(bookTest1)).thenReturn(bookTest1);

        Book result = bookService.createBook(bookTest1);

        assertEquals(bookTest1, bookController.createBook(bookTest1));
        assertNotNull(result);
        assertEquals(bookTest1, result);
        verify(bookService, times(2)).createBook(any(Book.class));
    }

    @Test
    void createBookWithIncompleteData() {
        bookTest2.setIsbn("INVALID_ISBN");
        when(bookService.createBook(bookTest2)).thenReturn(null);

        Book result = bookController.createBook(bookTest2);

        assertNull(result);
        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    void createBookWithNullFields() {
        bookTest3.setTitle(null);
        bookTest3.setAuthor(null);
        bookTest3.setPublisher(null);
        bookTest3.setFormat(null);

        Book expectedBook = new Book("3", "2070405370", "test", "test", "test", BookFormat.BROCHE, true);

        when(bookService.createBook(any(Book.class))).thenReturn(expectedBook);

        Book result = bookController.createBook(bookTest3);

        assertNotNull(result);
        assertEquals("test", result.getTitle());
        assertEquals("test", result.getAuthor());
        assertEquals("test", result.getPublisher());
        assertEquals(BookFormat.BROCHE, result.getFormat());
        verify(bookService, times(1)).createBook(any(Book.class));
    }
}
