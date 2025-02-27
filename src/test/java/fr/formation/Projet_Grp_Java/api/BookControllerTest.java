package fr.formation.Projet_Grp_Java.api;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.model.BookFormat;
import fr.formation.Projet_Grp_Java.repo.BookRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookControllerTest {

    private static final BookFormat POCHE = BookFormat.POCHE;

    @Mock
    private BookRepository bookRepository;

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
        bookTest2.setId("1");
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

}
