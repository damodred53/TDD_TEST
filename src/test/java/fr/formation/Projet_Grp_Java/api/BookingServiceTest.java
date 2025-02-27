package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.model.Booking;
import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.repo.BookRepository;
import fr.formation.Projet_Grp_Java.repo.BookingRepository;
import fr.formation.Projet_Grp_Java.repo.UtilisateurRepository;
import fr.formation.Projet_Grp_Java.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Utilisateur utilisateur;
    private Book book;
    private Booking activeBooking1;
    private Booking activeBooking2;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setId("user123");

        book = new Book();
        book.setId("book123");

        activeBooking1 = new Booking();
        activeBooking1.setUtilisateur(utilisateur);
        activeBooking1.setEnded(false);

        activeBooking2 = new Booking();
        activeBooking2.setUtilisateur(utilisateur);
        activeBooking2.setEnded(false);
    }

    @Test
    void createBookingWithLessThanThreeBookings() {
        // GIVEN :
        when(utilisateurRepository.findById("user123")).thenReturn(Optional.of(utilisateur));
        when(bookRepository.findById("book123")).thenReturn(Optional.of(book));

        List<Booking> activeBookings = Arrays.asList(activeBooking1, activeBooking2);
        when(bookingRepository.findByUtilisateurId("user123")).thenReturn(activeBookings);

        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN :
        Booking newBooking = bookingService.createBooking("user123", "book123");

        // THEN
        assertEquals(utilisateur, newBooking.getUtilisateur());
        assertEquals(book, newBooking.getBook());
        assertFalse(newBooking.isEnded());
        assertEquals(LocalDate.now().plusMonths(4), newBooking.getDueDate());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createBookingWithThreeOrMoreBookings_ShouldThrowException() {
        // GIVEN :
        Booking activeBooking3 = new Booking();
        activeBooking3.setUtilisateur(utilisateur);
        activeBooking3.setEnded(false);

        List<Booking> activeBookings = Arrays.asList(activeBooking1, activeBooking2, activeBooking3);
        when(utilisateurRepository.findById("user123")).thenReturn(Optional.of(utilisateur));
        when(bookRepository.findById("book123")).thenReturn(Optional.of(book));
        when(bookingRepository.findByUtilisateurId("user123")).thenReturn(activeBookings);

        // WHEN :
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking("user123", "book123");
        });

        assertEquals("L'utilisateur a déjà 3 réservations actives", exception.getMessage());

    }

    @Test
    void createBookingWithNonExistingUser_ShouldThrowException() {
        when(utilisateurRepository.findById("user123")).thenReturn(Optional.empty());
        // là aucun utilisateur n'est
        // renvoyé

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking("user123", "book123");
        });

        assertEquals("Utilisateur non trouvé", exception.getMessage());
    }

    @Test
    void createBookingWithNonExistingBook_ShouldThrowException() {
        when(utilisateurRepository.findById("user123")).thenReturn(Optional.of(utilisateur));
        when(bookRepository.findById("book123")).thenReturn(Optional.empty());
        // là aucun livre n'est renvoyé
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking("user123", "book123");
        });

        assertEquals("Livre non trouvé", exception.getMessage());
    }
}
