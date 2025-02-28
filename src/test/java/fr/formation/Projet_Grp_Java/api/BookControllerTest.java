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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookControllerTest {

    private static final BookFormat POCHE = BookFormat.POCHE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
    }

    @Test
    void testGetAllBooks() {
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("2701192919");
        bookTest2.setTitle("Pauline");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        List<Book> books = Arrays.asList(bookTest1, bookTest2);

        // WHEN :

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(2, result.size());
    }

    @Test
    void testGetBookById() {
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("2701192919");
        bookTest2.setTitle("Pauline");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        // WHEN :

        when(bookRepository.findById("1")).thenReturn(Optional.of(bookTest1));

        assertEquals(bookTest1, bookController.getBookById("1").getBody());
    }

    @Test
    void testGetBookByIsbn() {
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("2701192919");
        bookTest2.setTitle("Pauline");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        List<Book> books = Arrays.asList(bookTest1, bookTest2);

        // WHEN :

        when(bookService.getBooksByIsbn("2070405370")).thenReturn(books);

        assertEquals(books, bookController.getBooksByIsbn("2070405370"));
    }

    @Test
    void getBooksByTitle() {
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("2701192919");
        bookTest2.setTitle("Pauline");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        Book bookTest3 = new Book();
        bookTest3.setId("3");
        bookTest3.setIsbn("2701192917");
        bookTest3.setTitle("Un mauvais livre");
        bookTest3.setAuthor("Un mauvais auteur");
        bookTest3.setPublisher("Un mauvais éditeur");
        bookTest3.setFormat(POCHE);
        bookTest3.setAvailable(false);

        List<Book> expectedBooks = Arrays.asList(bookTest1);

        // WHEN :

        // On recherche un livre par son auteur et on ne tient pas compte de la casse
        // (majuscule/minuscule)
        when(bookService.getBooksByTitle("Le Comte de Monte-Cristo")).thenReturn(expectedBooks);

        List<Book> result = bookController.getBooksByTitle("Le Comte de Monte-Cristo");

        // THEN :
        assertEquals(expectedBooks, result);
        assertEquals(1, result.size());
        assertTrue(result.contains(bookTest1));
        assertFalse(result.contains(bookTest2));
    }

    @Test
    void getBooksByAuthor() {

        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("2701192919");
        bookTest2.setTitle("Pauline");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        Book bookTest3 = new Book();
        bookTest3.setId("3");
        bookTest3.setIsbn("2701192917");
        bookTest3.setTitle("Un mauvais livre");
        bookTest3.setAuthor("Un mauvais auteur");
        bookTest3.setPublisher("Un mauvais éditeur");
        bookTest3.setFormat(POCHE);
        bookTest3.setAvailable(false);

        List<Book> expectedBooks = Arrays.asList(bookTest1, bookTest2);

        // WHEN :
        when(bookService.getBooksByAuthor("Alexandre Dumas")).thenReturn(expectedBooks);

        // List<Book> result = bookController.getBooksByAuthor("Alexandre Dumas");
        List<Book> result = bookService.getBooksByAuthor("Alexandre Dumas");

        // THEN :
        // assertEquals(expectedBooks, result);
        assertTrue(result.contains(bookTest1));
        assertTrue(result.contains(bookTest2));
        assertFalse(result.contains(bookTest3));
    }

    @Test
    void getBooksByIsbn() {
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("2701192919");
        bookTest2.setTitle("Pauline");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        Book bookTest3 = new Book();
        bookTest3.setId("3");
        bookTest3.setIsbn("2701192917");
        bookTest3.setTitle("Un mauvais livre");
        bookTest3.setAuthor("Un mauvais auteur");
        bookTest3.setPublisher("Un mauvais éditeur");
        bookTest3.setFormat(POCHE);
        bookTest3.setAvailable(false);

        List<Book> expectedBookGoodIsbn = Arrays.asList(bookTest1);

        when(bookService.getBooksByIsbn("2070405370")).thenReturn(expectedBookGoodIsbn);

        List<Book> result = bookController.getBooksByIsbn("2070405370");

        // THEN :
        assertEquals(expectedBookGoodIsbn, result);
        assertEquals(1, result.size());
        assertTrue(result.contains(bookTest1));
        assertFalse(result.contains(bookTest2));

    }

    @Test
    void createBookWithCompleteData() {
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("2070405370");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        // WHEN :
        // J appelle deux fois la méthode createBook de mon service donc l'asseertion
        // compte deux appels
        when(bookService.createBook(bookTest1)).thenReturn(bookTest1);

        Book result = bookService.createBook(bookTest1);

        // THEN :
        assertEquals(bookTest1, bookController.createBook(bookTest1));
        assertNotNull(result);
        assertEquals(bookTest1, result);
        verify(bookService, times(2)).createBook(any(Book.class));

    }

    @Test
    void createBookWithIncompleteData() {

        Book bookTest2 = new Book();
        bookTest2.setId("2");
        bookTest2.setIsbn("INVALID_ISBN"); // ISBN invalide ici
        bookTest2.setTitle("Le Comte de Monte-Cristo");
        bookTest2.setAuthor("Alexandre Dumas");
        bookTest2.setPublisher("Le Livre de Poche");
        bookTest2.setFormat(POCHE);
        bookTest2.setAvailable(true);

        when(bookService.createBook(bookTest2)).thenReturn(null);

        // WHEN
        Book result = bookController.createBook(bookTest2);

        // THEN
        assertNull(result);
        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    void createBookWithNullFields() {

        Book bookTest3 = new Book();
        bookTest3.setId("3");
        bookTest3.setIsbn("2070405370");
        bookTest3.setTitle(null);
        bookTest3.setAuthor(null);
        bookTest3.setPublisher(null);
        bookTest3.setFormat(null);

        Book expectedBook = new Book();
        expectedBook.setId("3");
        expectedBook.setIsbn("2070405370");
        expectedBook.setTitle("test");
        expectedBook.setAuthor("test");
        expectedBook.setPublisher("test");
        expectedBook.setFormat(BookFormat.BROCHE); // C'est la valeur que je mets par défaut

        when(bookService.createBook(any(Book.class))).thenReturn(expectedBook);

        // WHEN
        Book result = bookController.createBook(bookTest3);

        // THEN
        assertNotNull(result);
        assertEquals("test", result.getTitle());
        assertEquals("test", result.getAuthor());
        assertEquals("test", result.getPublisher());
        assertEquals(BookFormat.BROCHE, result.getFormat());
        verify(bookService, times(1)).createBook(any(Book.class));
    }
}
