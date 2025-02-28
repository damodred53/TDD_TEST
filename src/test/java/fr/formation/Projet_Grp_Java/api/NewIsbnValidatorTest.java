package fr.formation.Projet_Grp_Java.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.formation.Projet_Grp_Java.exception.InvalidIsbnCharacterException;
import fr.formation.Projet_Grp_Java.exception.InvalidIsbnLengthException;
import fr.formation.Projet_Grp_Java.service.NewIsbnValidator;

public class NewIsbnValidatorTest {

    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {

        NewIsbnValidator newValidator = new NewIsbnValidator();
        // WHEN
        boolean result = newValidator.validateNewIsbn("9782070453948");
        // THEN
        assertTrue(result);
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
