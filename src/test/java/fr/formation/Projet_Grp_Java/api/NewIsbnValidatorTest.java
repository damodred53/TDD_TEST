package fr.formation.Projet_Grp_Java.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.formation.Projet_Grp_Java.exception.InvalidIsbnCharacterException;
import fr.formation.Projet_Grp_Java.exception.InvalidIsbnLengthException;
import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.model.BookFormat;
import fr.formation.Projet_Grp_Java.service.BookService;
import fr.formation.Projet_Grp_Java.service.NewIsbnValidator;

public class NewIsbnValidatorTest {

    @Mock
    BookService bookService;

    private static final BookFormat POCHE = BookFormat.POCHE;

    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {

        NewIsbnValidator newValidator = new NewIsbnValidator();
        // WHEN
        boolean result = newValidator.validateNewIsbn("9782070453948");
        // THEN
        assertTrue(result);
    }

    @Test
    public void whenIsbnIsValidWithBare_shouldReturnTrue() {
        // GIVEN : Création d'un livre avec un ISBN contenant des tirets
        Book bookTest1 = new Book();
        bookTest1.setId("1");
        bookTest1.setIsbn("978-2290391174");
        bookTest1.setTitle("Le Comte de Monte-Cristo");
        bookTest1.setAuthor("Alexandre Dumas");
        bookTest1.setPublisher("Le Livre de Poche");
        bookTest1.setFormat(POCHE);
        bookTest1.setAvailable(true);

        NewIsbnValidator newValidator = new NewIsbnValidator();

        BookService bookService = new BookService(null, null, newValidator);

        // WHEN :
        bookTest1.setIsbn(bookService.deleteBareIsbn(bookTest1.getIsbn()));
        boolean result = newValidator.validateNewIsbn(bookTest1.getIsbn());

        // THEN
        assertTrue(result);
        assertFalse(bookTest1.getIsbn().contains("-"));
    }

    @Test
    public void whenIsbnIsInvalid_shouldReturnFalse() {

        NewIsbnValidator newValidator = new NewIsbnValidator();
        // WHEN
        boolean result = newValidator.validateNewIsbn("9782070453944");
        // THEN
        assertFalse(result);
    }

    @Test
    public void whenIsbnIs12Chars_shouldThrowInvalidLengthException() {

        NewIsbnValidator newValidator = new NewIsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnLengthException.class, () -> newValidator.validateNewIsbn("782070453948"));
    }

    @Test
    public void whenIsbnIsValidAndEndsWithX_shouldNotReturnTrue() {

        NewIsbnValidator newValidator = new NewIsbnValidator();
        // WHEN
        // Le dernier chiffre ne doit pas être un X à la fin en isbn 13
        assertThrows(InvalidIsbnCharacterException.class, () -> newValidator.validateNewIsbn("782070453948X"));
    }

    @Test
    public void whenIsbnHasInvalidChar_shouldThrowException() {

        NewIsbnValidator newValidator = new NewIsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnCharacterException.class, () -> newValidator.validateNewIsbn("14ze74577456X"));
    }
}
