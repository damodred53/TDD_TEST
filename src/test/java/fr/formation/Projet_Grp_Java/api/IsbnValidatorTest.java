package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.exception.InvalidIsbnCharacterException;
import fr.formation.Projet_Grp_Java.exception.InvalidIsbnLengthException;
import fr.formation.Projet_Grp_Java.service.IsbnValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IsbnValidatorTest {

    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        boolean result = validator.validateIsbn("2253009687");
        // THEN
        assertTrue(result);
    }

    @Test
    public void whenIsbnIsInvalid_shouldReturnFalse() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        boolean result = validator.validateIsbn("2253009684");
        // THEN
        assertFalse(result);
    }

    @Test
    public void whenIsbnIs9Chars_shouldThrowInvalidLengthException() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnLengthException.class, () -> validator.validateIsbn("225300968"));
    }

    @Test
    public void whenIsbnIsValidAndEndsWithX_shouldReturnTrue() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        boolean result = validator.validateIsbn("140274577X");
        // THEN
        assertTrue(result);
    }

    @Test
    public void whenIsbnHasInvalidChar_shouldThrowException() {

        IsbnValidator validator = new IsbnValidator();
        // WHEN
        assertThrows(InvalidIsbnCharacterException.class, () -> validator.validateIsbn("14ze74577X"));
    }

}
